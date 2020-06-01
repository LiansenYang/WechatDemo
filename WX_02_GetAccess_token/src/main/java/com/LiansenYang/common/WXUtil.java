package com.LiansenYang.common;

import com.LiansenYang.cache.ICache;
import com.LiansenYang.cache.SimpleAgedObjectCache;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class WXUtil {
    private static WXUtil ourInstance = new WXUtil();

    public static WXUtil getInstance() {
        return ourInstance;
    }

    private WXUtil() {
    }

    public final static Log log = LogFactory.getLog(WXUtil.class);
    private ICache objectCache = new SimpleAgedObjectCache("ObjectCache");
    private Object objectCacheLock = new Object();

    private String getAccessToken(){

        String turl = String.format("%sgrant_type=client_credential&appid=%s&secret=%s",
                PropertiesUtil.getAppConfig("weixin.req.url.token"),
                PropertiesUtil.getAppConfig("weixin.app.id"),
                PropertiesUtil.getAppConfig("weixin.app.secret"));
        HttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(turl);
        String result = null;
        try {
            HttpResponse res = client.execute(get);
            String responseContent = null; // 响应内容
            HttpEntity entity = res.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
            System.out.println(responseContent);

            JSONObject json = JSON.parseObject(responseContent);
            /*JsonObject json = jsonparer.parse(responseContent).getAsJsonObject();*/
            // 将json字符串转换为json对象
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                if (json.get("errcode")!=null&&!json.get("errcode").equals("0")) {// 错误时微信会返回错误码等信息，{"errcode":40013,"errmsg":"invalid appid"}
                } else {// 正常情况下{"access_token":"ACCESS_TOKEN","expires_in":7200}
                    result = json.get("access_token").toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭连接 ,释放资源
            client.getConnectionManager().shutdown();
            return result;
        }
    }
    public static String getTicket(String access_token) throws Exception {
        StringBuilder url = new StringBuilder();
        url.append( PropertiesUtil.getAppConfig("weixin.req.url.getticket"))
                .append("type=").append("jsapi")
                .append("&access_token=").append(access_token);
        HttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(url.toString());
        String result = null;
        try {
            HttpResponse res = client.execute(get);
            String responseContent = null; // 响应内容
            HttpEntity entity = res.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
            System.out.println(responseContent);

            JSONObject json = JSON.parseObject(responseContent);
            /*JsonObject json = jsonparer.parse(responseContent).getAsJsonObject();*/
            // 将json字符串转换为json对象
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                if (json.get("errcode") != null) {// 错误时微信会返回错误码等信息，{"errcode":40013,"errmsg":"invalid appid"}
                    result = json.get("ticket").toString();
                } else {// 正常情况下{"access_token":"ACCESS_TOKEN","expires_in":7200}

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭连接 ,释放资源
            client.getConnectionManager().shutdown();
            return result;
        }
    }


    public Map getWeixinParameterMapFromCache(){
        if (log.isDebugEnabled())
            log.debug("Retrieving WeixinParameter from cache.");
        Map map = (Map) objectCache.get("WeixinParameter");
        if (map == null || StringUtils.isEmpty((String)map.get("access_token"))) {
            synchronized (objectCacheLock) {
                if (log.isDebugEnabled())
                    log.debug("Retrieving WeixinParameter again from cache.");
                map = (Map) objectCache.get("WeixinParameter");
                if (map == null || StringUtils.isEmpty((String)map.get("access_token"))) {
                    if (log.isDebugEnabled())
                        log.debug("WeixinParameter is not in the cache,create the map.");
                    map = new HashMap();


                    String access_token = null;
                    try {
                        access_token =  getAccessToken();

                        log.info("WeixinParameter access_token is created. "+access_token);
                        log.info("WeixinParameter access_token is created successfully ");

                        map.put("access_token", access_token);
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.info("WeixinParameter access_token create false. ");
                    }
                    try {
                        String ticket = getTicket(access_token);

                        log.info("WeixinParameter ticket is created. "+ticket);
                        log.info("WeixinParameter ticket is created successfully ");

                        map.put("ticket", ticket);
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.info("WeixinParameter ticket create false. ");
                    }

                    objectCache.put("WeixinParameter", map, 120);
                }
            }
        } else {
            log.info("WeixinParameter return from cache. "+map);
        }
        return map;
    }
}
