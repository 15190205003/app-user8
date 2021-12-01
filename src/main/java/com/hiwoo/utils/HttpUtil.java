package com.hiwoo.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.Charset;
import java.util.ResourceBundle;

@Configuration
public class HttpUtil {

    protected static final Logger LOG = LoggerFactory.getLogger(HttpUtil.class);

    private static String DEVOPS_URL;

    private static Integer IsPrivatization;

    private static String MIDDLEWARE_URL;

    private static String APPID;

    private static String APPSECRET;

    public String getDevopsUrl() {
        return DEVOPS_URL;
    }

    @Value("${Devops.server.url}")
    public void setDevopsUrl(String url) {
        HttpUtil.DEVOPS_URL = url;
    }

    public static Integer getIsPrivatization() {
        return IsPrivatization;
    }

    @Value("${IsPrivatization}")
    public void setIsPrivatization(Integer IsPrivatization) {
        HttpUtil.IsPrivatization = IsPrivatization;
    }

    public String getMiddlewareUrl() {
        return MIDDLEWARE_URL;
    }

    @Value("${middelware.server.url}")
    public void setMiddlewareUrl(String url) {
        HttpUtil.MIDDLEWARE_URL = url;
    }

    public String getAPPID() {
        return APPID;
    }

    @Value("${middelware.appid}")
    public void setAPPID(String appid) {
        HttpUtil.APPID = appid;
    }

    public String getAPPSECRET() {
        return APPSECRET;
    }

    @Value("${middelware.appsecret}")
    public void setAPPSECRET(String appsecret) {
        HttpUtil.APPSECRET = appsecret;
    }

    /**
     * 中间件认证头
     *
     * @return
     */
    private static String getHeaderMW(String appId, String appSecret) {
        String authHeader = null;
        if (0 == IsPrivatization) {
            if (appId != null && appSecret != null) {
                String auth = appId + ":" + appSecret;
                byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
                authHeader = "Basic " + new String(encodedAuth);
            }
        } else {
            String auth = APPID + ":" + APPSECRET;
            byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
            authHeader = "Basic " + new String(encodedAuth);
        }

        return authHeader;
    }

    /**
     * 中间件post请求
     *
     * @param body
     * @return
     * @throws Exception
     */
    public static String sendHttpPostForMW(String path, String body, String groupId, String appId, String appSecret) throws Exception {
        String url = MIDDLEWARE_URL + path;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Authorization", getHeaderMW(appId, appSecret));
        httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
        if (StringUtils.isNotEmpty(groupId)) {
            httpPost.addHeader("AuthGroupId", groupId);
        }
        httpPost.setEntity(new StringEntity(body, Charset.forName("UTF-8")));
        CloseableHttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String responseContent = EntityUtils.toString(entity, "UTF-8");
        response.close();
        httpClient.close();
        return responseContent;
    }

    /**
     * 运维后台post请求
     *
     * @param body
     * @return
     * @throws Exception
     */
    public static String sendHttpPostForDevops(String path, String body) throws Exception {
        String url = DEVOPS_URL + path;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
        if (StringUtils.isNotEmpty(body)) {
            httpPost.setEntity(new StringEntity(body, Charset.forName("UTF-8")));
        }
        CloseableHttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String responseContent = EntityUtils.toString(entity, "UTF-8");
        response.close();
        httpClient.close();
        return responseContent;
    }
}
