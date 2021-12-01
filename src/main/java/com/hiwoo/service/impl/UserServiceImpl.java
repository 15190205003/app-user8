package com.hiwoo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hiwoo.entity.User;
import com.hiwoo.mapper.UserMapper;
import com.hiwoo.model.DataAuth;
import com.hiwoo.model.UserInfo;
import com.hiwoo.service.UserService;
import com.hiwoo.utils.HttpUtil;
import com.hiwoo.utils.PageResult;
import com.hiwoo.utils.PasswordHelper;
import com.hiwoo.utils.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    Map<String, Boolean> authCacheMap = new HashMap<String, Boolean>();

    private PasswordHelper passwordHelper = new PasswordHelper();


    @Override
    public void updateUserInfo(User user) {
        userMapper.updateByPrimaryKey(user);
    }

    @Override
    public User getUserInfo(int userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public PageResult<User> searchUserByPage(String queryName, String tag, String appId, int pageNumber, int pageSize) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("deleteTag", 0).andEqualTo("appId", appId);

        if (StringUtils.isNotEmpty(queryName)) {
            example.and(example.createCriteria()
                    .andLike("userName", '%' + queryName + '%')
                    .orLike("account", "%" + queryName + "%"));
        }

        if (StringUtils.isNotEmpty(tag)) {
            example.and(example.createCriteria()
                    .andLike("tag", tag + "_%")
                    .orEqualTo("tag", tag));
        }
        example.orderBy("createTime").desc();

        PageHelper.startPage(pageNumber, pageSize);
        List<User> users = userMapper.selectByExample(example);
        PageResult<User> pageResult = new PageResult<User>();
        pageResult.setResults(users);
        PageInfo<User> pageInfo = new PageInfo<User>(users);
        pageResult.setTotal(pageInfo.getTotal());
        pageResult.setPageNumber(pageInfo.getPageNum());
        pageResult.setPageSize(pageInfo.getPageSize());
        return pageResult;
    }

    @Override
    public List<User> searchUserList(String queryName, String tag, String appId) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("deleteTag", 0).andEqualTo("appId", appId).andNotEqualTo("role", 2);

        if (StringUtils.isNotEmpty(queryName)) {
            example.and(example.createCriteria()
                    .andLike("userName", '%' + queryName + '%')
                    .orLike("account", "%" + queryName + "%"));
        }

        if (StringUtils.isNotEmpty(tag)) {
            example.and(example.createCriteria()
                    .andLike("tag", tag + "_%")
                    .orEqualTo("tag", tag));
        }
        return userMapper.selectByExample(example);
    }

    @Override
    public String getNewPassword(User user) {
        String pw = passwordHelper.getNewPassword(user);
        return pw;
    }

    @Override
    public Integer getUserCountByAppId(String appId, Integer deleteTag) {
        Example example = new Example(User.class);
        Example.Criteria cri = example.createCriteria();
        if (StringUtils.isNotEmpty(appId)) {
            cri.andEqualTo("appId", appId);
        }
        cri.andEqualTo("deleteTag", deleteTag);
        Integer count = userMapper.selectByExample(example).size();
        return count;
    }

    @Override
    public User getByAccount(String account) {
        Example example = new Example(User.class);
        Example.Criteria cri = example.createCriteria();
        cri.andEqualTo("account", account).andEqualTo("deleteTag", 0);
        List<User> users = userMapper.selectByExample(example);
        if (users != null && users.size() > 0) {
            return users.get(0);
        }
        return null;
    }

    @Override
    public void setLocked(int id, Integer locked) {

        User user = userMapper.selectByPrimaryKey(id);
        if (user != null) {
            user.setLocked(locked);
            userMapper.updateByPrimaryKey(user);
        }
    }


    @Override
    public void changePassword(String account, String newPassword) {

        User user = getByAccount(account);
        user.setPassword(newPassword);
        passwordHelper.encryptPassword(user);
        userMapper.updateByPrimaryKey(user);
    }

    @Override
    public UserInfo getUserByDevops(String account, String password) {
        try {
            JSONObject paramObj = new JSONObject();
            UserInfo userInfo = null;
            paramObj.put("account", account);
            paramObj.put("password", password);
            paramObj.put("accType", 2);
            String body = JSON.toJSONString(paramObj);
            String responseBody = HttpUtil.sendHttpPostForDevops("/devOps/AccAuth", body);
            if (null != responseBody) {
                JSONObject jsonObject = JSONObject.parseObject(responseBody);
                String code = jsonObject.getString("code");
                if (null != code && !("".equals(code)) && "0000".equals(code)) {
                    JSONObject attach = jsonObject.getJSONObject("attachObject");
                    JSONObject dataAuth = attach.getJSONObject("dataAuth");
                    userInfo = JSON.parseObject(JSON.toJSONString(attach), UserInfo.class);
                    userInfo.setDataAuth(JSON.parseObject(JSON.toJSONString(dataAuth), DataAuth.class));
                    return userInfo;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    @Override
    public UserInfo getUserDevopsByUserId(Integer userId) {
        try {
            UserInfo userInfo = null;
            JSONObject paramObj = new JSONObject();
            paramObj.put("userId", userId);
            String body = JSON.toJSONString(paramObj);
            String responseBody = HttpUtil.sendHttpPostForDevops("/devOps/getUserInfoById", body);
            if (null != responseBody) {
                JSONObject result = JSONObject.parseObject(responseBody);
                if (null != result) {
                    String code = result.getString("code");
                    if ("0000".equals(code)) {
                        JSONObject attach = result.getJSONObject("attachObject");
                        if (null != attach) {
                            userInfo = JSON.parseObject(JSON.toJSONString(attach), UserInfo.class);
                        }
                    }
                }
            }
            return userInfo;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public UserInfo checkAuthInfo(String appId, String appSecret) {
        try {
            UserInfo userInfo = null;
            JSONObject paramObj = new JSONObject();
            paramObj.put("appId", appId);
            paramObj.put("appSecret", appSecret);
            String body = JSON.toJSONString(paramObj);
            String responseBody = HttpUtil.sendHttpPostForDevops("/devOps/getInfoByAppId", body);
            if (null != responseBody) {
                JSONObject result = JSONObject.parseObject(responseBody);
                if (null != result) {
                    String code = result.getString("code");
                    if ("0000".equals(code)) {
                        JSONObject attach = result.getJSONObject("attachObject");
                        if (null != attach) {
                            userInfo = JSON.parseObject(JSON.toJSONString(attach), UserInfo.class);
                        }
                    }
                }
            }
            return userInfo;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean getUserByAccount(String account) {
        try {
            boolean bool = false;
            JSONObject paramObj = new JSONObject();
            paramObj.put("account", account);
            String body = JSON.toJSONString(paramObj);
            String responseBody = HttpUtil.sendHttpPostForDevops("/devOps/getUserInfoByaccount", body);
            if (!responseBody.equals("false")) {
                bool = true;
            }
            return bool;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }


    @Override
    public Boolean getAuthenticationCache(String account, String pwd) {
        String key = "auth_" + account + "_" + pwd;
        if (authCacheMap.containsKey(key)) {
            return true;
        }
        return false;
    }

    @Override
    public void setAuthenticationCache(String account, String pwd) {
        String key = "auth_" + account + "_" + pwd;
        authCacheMap.put(key, true);
    }

    @Override
    public void clearAuthenticationCache() {
        authCacheMap = new HashMap<String, Boolean>();
    }

    @Override
    public int deleteUserById(User user) {
        return userMapper.deleteUserById(user);
    }

    @Override
    public int deleteByPrimaryKey(Object o) {
        return userMapper.deleteByPrimaryKey(o);
    }

    @Override
    public int delete(User user) {
        return userMapper.delete(user);
    }

    @Override
    public int insert(User user) {

        passwordHelper.encryptPassword(user);
        return userMapper.insert(user);
    }

    @Override
    public int insertSelective(User user) {
        return userMapper.insertSelective(user);
    }

    @Override
    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    @Override
    public User selectByPrimaryKey(Object o) {
        return userMapper.selectByPrimaryKey(o);
    }

    @Override
    public int selectCount(User user) {
        return userMapper.selectCount(user);
    }

    @Override
    public List<User> select(User user) {
        return userMapper.select(user);
    }

    @Override
    public User selectOne(User user) {
        return userMapper.selectOne(user);
    }

    @Override
    public int updateByPrimaryKey(User user) {
        return userMapper.updateByPrimaryKey(user);
    }

    @Override
    public int updateByPrimaryKeySelective(User user) {
        return userMapper.updateByPrimaryKey(user);
    }

    @Override
    public boolean existsWithPrimaryKey(Object o) {
        return false;
    }
}
