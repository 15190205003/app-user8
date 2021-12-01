package com.hiwoo.shiro.cache;

import com.hiwoo.utils.LoggerUtils;
import com.hiwoo.utils.SerializeUtil;
import com.hiwoo.utils.StringUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


/**
 *
 *
 * Redis Manager Utils
 *
 * <p>
 *
 */
public class JedisManager {

    private static final int DB_INDEX = 3;
    private static final int DB_INDEX_SESSION = 4;

    @Autowired
    private JedisPool jedisPool;

    public Jedis getJedis() {
        Jedis jedis = null;
        try {
            jedis = getJedisPool().getResource();
        } catch (JedisConnectionException e) {
            String message = StringUtils.trim(e.getMessage());
            if("Could not get a resource from the pool".equalsIgnoreCase(message)){
                LoggerUtils.error(getClass(),message);
            }
            throw new JedisConnectionException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return jedis;
    }

    public void returnResource(Jedis jedis, boolean isBroken) {
        if (jedis == null)
            return;
        /**
         * @deprecated starting from Jedis 3.0 this method will not be exposed.
         * Resource cleanup should be done using @see {@link redis.clients.jedis.Jedis#close()}
        if (isBroken){
        getJedisPool().returnBrokenResource(jedis);
        }else{
        getJedisPool().returnResource(jedis);
        }
         */
        jedis.close();
    }

    public byte[] getValueByKey(int dbIndex, byte[] key) throws Exception {
        Jedis jedis = null;
        byte[] result = null;
        boolean isBroken = false;
        try {
            jedis = getJedis();
            jedis.select(dbIndex);
            result = jedis.get(key);
        } catch (Exception e) {
            isBroken = true;
            throw e;
        } finally {
            returnResource(jedis, isBroken);
        }
        return result;
    }

    public void deleteByKey(int dbIndex, byte[] key) throws Exception {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = getJedis();
            jedis.select(dbIndex);
            Long result = jedis.del(key);
            LoggerUtils.fmtDebug(getClass(), "删除Session结果：%s" , result);
        } catch (Exception e) {
            isBroken = true;
            throw e;
        } finally {
            returnResource(jedis, isBroken);
        }
    }

    public void saveValueByKey(int dbIndex, byte[] key, byte[] value, int expireTime)
            throws Exception {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = getJedis();
            jedis.select(dbIndex);
            jedis.set(key, value);
            if (expireTime > 0)
                jedis.expire(key, expireTime);
        } catch (Exception e) {
            isBroken = true;
            throw e;
        } finally {
            returnResource(jedis, isBroken);
        }
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    /**
     * 获取所有Session
     * @param dbIndex
     * @param redisShiroSession
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public Collection<Session> AllSession(int dbIndex, String redisShiroSession) throws Exception {
        Jedis jedis = null;
        boolean isBroken = false;
        Set<Session> sessions = new HashSet<Session>();
        try {
            jedis = getJedis();
            jedis.select(dbIndex);

            Set<byte[]> byteKeys = jedis.keys((JedisShiroSessionRepository.REDIS_SHIRO_ALL).getBytes());
            if (byteKeys != null && byteKeys.size() > 0) {
                for (byte[] bs : byteKeys) {
                    Session obj = SerializeUtil.deserialize(jedis.get(bs),
                            Session.class);
                    if(obj instanceof Session){
                        sessions.add(obj);
                    }
                }
            }
        } catch (Exception e) {
            isBroken = true;
            throw e;
        } finally {
            returnResource(jedis, isBroken);
        }
        return sessions;
    }


    public String getValuesByKey(String key){
        Jedis jedis = null;
        String result = null;
        boolean isBroken = false;
        try {
            jedis = getJedis();
            jedis.select(DB_INDEX);
            result = jedis.get(key);
        } catch (Exception e) {
            isBroken = true;
            throw e;
        } finally {
            returnResource(jedis, isBroken);
        }
        return result;
    }

    public void setValuesByKey(String key, String value){
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = getJedis();
            jedis.select(DB_INDEX);
            jedis.set(key, value);
        } catch (Exception e) {
            isBroken = true;
            throw e;
        } finally {
            returnResource(jedis, isBroken);
        }
    }

    public void setValuesByKey(String key, String value, int expireTime){
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = getJedis();
            jedis.select(DB_INDEX);
            jedis.set(key, value);
            if(expireTime > 0){
                jedis.expire(key, expireTime);
            }
        } catch (Exception e) {
            isBroken = true;
            throw e;
        } finally {
            returnResource(jedis, isBroken);
        }
    }

    public void deleteValuesByKey(String key){
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = getJedis();
            jedis.select(DB_INDEX);
            Set<String> keys = jedis.keys(key);
            for(String keystr : keys){
                jedis.del(keystr);
            }
        } catch (Exception e) {
            isBroken = true;
            throw e;
        } finally {
            returnResource(jedis, isBroken);
        }
    }


    public String getSessionValuesByKey(String key){
        Jedis jedis = null;
        String result = null;
        boolean isBroken = false;
        try {
            jedis = getJedis();
            jedis.select(DB_INDEX_SESSION);
            result = jedis.get(key);
        } catch (Exception e) {
            isBroken = true;
            throw e;
        } finally {
            returnResource(jedis, isBroken);
        }
        return result;
    }

    public void setSessionValuesByKey(String key, String value){
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = getJedis();
            jedis.select(DB_INDEX_SESSION);
            jedis.set(key, value);
        } catch (Exception e) {
            isBroken = true;
            throw e;
        } finally {
            returnResource(jedis, isBroken);
        }
    }

    public void setSessionValuesByKey(String key, String value, int expireTime){
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = getJedis();
            jedis.select(DB_INDEX_SESSION);
            jedis.set(key, value);
            if(expireTime > 0){
                jedis.expire(key, expireTime);
            }
        } catch (Exception e) {
            isBroken = true;
            throw e;
        } finally {
            returnResource(jedis, isBroken);
        }
    }

    public void deleteSessionValuesByKey(String key){
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = getJedis();
            jedis.select(DB_INDEX_SESSION);
            Set<String> keys = jedis.keys(key);
            for(String keystr : keys){
                jedis.del(keystr);
            }
        } catch (Exception e) {
            isBroken = true;
            throw e;
        } finally {
            returnResource(jedis, isBroken);
        }
    }

}
