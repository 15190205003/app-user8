package com.hiwoo.filter;


import com.hiwoo.entity.User;
import com.hiwoo.model.UserInfo;
import com.hiwoo.service.LicenseService;
import com.hiwoo.service.UserService;
import com.hiwoo.shiro.cache.JedisManager;
import com.hiwoo.utils.ErrorCodeEnum;
import com.hiwoo.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


@WebFilter(urlPatterns = {"/*"}, filterName = "LoginFilter", initParams = {})
public class LoginFilter implements Filter {

    protected final Logger LOG = LoggerFactory.getLogger(LoginFilter.class);

    @Autowired
    private UserService userService;

    @Autowired
    private JedisManager jedisManager;

    @Autowired
    private LicenseService licenseService;

    @Value("${IsPrivatization}")
    private Integer IsPrivatization;

    @Value("${experience.user}")
    private String experience;

    @Override
    public void init(FilterConfig config) throws ServletException {

        //加上它filter中才能加载service的东西
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest hReq = (HttpServletRequest) request;
        HttpServletResponse hResp = (HttpServletResponse) response;
        hResp.setHeader("Access-Control-Allow-Origin", "*");
        hResp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        hResp.setHeader("Access-Control-Max-Age", "3600");
        hResp.setHeader("Access-Control-Allow-Headers", "x-requested-with,Content-Type,Authorization,Accept,Token,WechatAuth,AuthGroupId");

        String authorizationStr = hReq.getHeader("Authorization");
        String token = hReq.getHeader("Token");
        String wechatAuth = hReq.getHeader("WechatAuth");
        String path = ((HttpServletRequest) request).getRequestURI();

        String userName = "";
        String pwd = "";

        if (StringUtils.isNotEmpty(authorizationStr)) {
            String[] strArr = authorizationStr.split(" ");
            Base64.Decoder decoder = Base64.getDecoder();
            if (strArr.length > 0 && StringUtils.isNotEmpty(strArr[1])) {
                String userAndPwdStr = new String(decoder.decode(strArr[1]), "UTF-8");
                String[] userInfoArr = userAndPwdStr.split(":");
                if (null == userInfoArr || userInfoArr.length == 0) {
                    exceptionReturn(hReq, hResp, ErrorCodeEnum.ACCOUNT_IS_EMPTY.getCode()); //用户密码不能为空
                    return;
                }
                userName = StringUtils.trim(userInfoArr[0]);
                pwd = StringUtils.trim(userInfoArr[1]);
                LOG.info(userName + "=:=" + pwd);
            }
        }
        if (!(path.indexOf("/user/getAuthentication") >= 0 && userName.equals(experience))) {
            if (path.indexOf("/user/loginCommoner") >= 0 || path.indexOf("/user/LoginWeChat") >= 0 || path.indexOf("/user/LoginApp") >= 0 || path.indexOf("/user/getAuthentication") >= 0 || path.indexOf("/user/getAuthAndFindInfo") >= 0 ||
                    path.indexOf("/role/getRoleJurisdiction") >= 0 || path.indexOf("/role/getRoleList") >= 0 || path.indexOf("/user/isAccount") >= 0 || path.indexOf("/user/getUserInfo") >= 0) {
                filterChain.doFilter(request, response);
                return;
            } else {
                String tokenCache = "";
                //微信和pc端允许同一账号同时登录
                String accountLower = userName.toLowerCase();
                if (null != wechatAuth && "TRUE".equals(wechatAuth)) {
                    tokenCache = jedisManager.getSessionValuesByKey("wx_token_" + accountLower);
                    if (null != tokenCache && null != token && !tokenCache.equals(token)) {
                        hResp.addHeader("Access-Control-Expose-Headers", "REDIRECT,CONTEXTPATH");
                        hResp.addHeader("REDIRECT", "REDIRECT_WX");
                        return;
                    }
                } else {
                    tokenCache = jedisManager.getSessionValuesByKey("pc_token_" + accountLower);
                    if (null != tokenCache && null != token && !tokenCache.equals(token)) {
                        hResp.addHeader("Access-Control-Expose-Headers", "REDIRECT,CONTEXTPATH");
                        hResp.addHeader("REDIRECT", "REDIRECT");
                        return;
                    }
                }
            }

            if (!userService.getAuthenticationCache(userName, pwd)) {
                if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(pwd)) {
                    exceptionReturn(hReq, hResp, ErrorCodeEnum.ACCOUNT_IS_EMPTY.getCode()); //用户密码不能为空
                    return;
                } else {
                    User userInfo = userService.getByAccount(userName);
                    if (IsPrivatization == 0) {
                        if (null == userInfo) {
                            userInfo = new User();
                            UserInfo userInfo1 = userService.getUserByDevops(userName, pwd);
                            if (null == userInfo1 || StringUtils.isEmpty(userInfo1.getAccount())) {
                                exceptionReturn(hReq, hResp, ErrorCodeEnum.ACCOUNT_IS_NOTEXIST.getCode()); //用户不存在
                                return;
                            }
                            userInfo.setId(userInfo1.getId());
                            userInfo.setAccount(userInfo1.getAccount());
                            userInfo.setPassword(userInfo1.getPassword());
                            userInfo.setRole(userInfo1.getRole());
                        }
                    }

                    if (null == userInfo || StringUtils.isEmpty(userInfo.getAccount())) {
                        exceptionReturn(hReq, hResp, ErrorCodeEnum.ACCOUNT_IS_NOTEXIST.getCode()); //用户不存在
                        return;
                    }
                }
                userService.setAuthenticationCache(userName, pwd);
            }
            filterChain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }

    /**
     * 跳转到错误页面
     *
     * @param request
     * @param response
     * @param errorType
     */
    private void exceptionReturn(HttpServletRequest request, HttpServletResponse response, int errorType) {

        try {
            String url = "/MSG/excepMsg?errorType=" + errorType;
            request.getRequestDispatcher(url).forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }

    public void redirectReturn(HttpServletResponse hResp, String wechatAuth, String returnType) {
        if (null != wechatAuth && "TRUE".equals(wechatAuth)) {
            hResp.addHeader("Access-Control-Expose-Headers", "REDIRECT,CONTEXTPATH");
            hResp.addHeader("REDIRECT", "REDIRECT_WX");
        } else {
            hResp.addHeader("Access-Control-Expose-Headers", "REDIRECT,CONTEXTPATH");
            hResp.addHeader("REDIRECT", returnType);
        }
        return;
    }

}
