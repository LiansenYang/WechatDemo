package com.LiansenYang.common;

import com.LiansenYang.cache.ICache;
import com.LiansenYang.cache.SimpleAgedObjectCache;
import com.google.gson.JsonObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

//获取微信接口,用spring容器默认单例装载
@Component
public class WXUtil {
    private ICache objectCache = new SimpleAgedObjectCache("ObjectCache");
    private Object objectCacheLock = new Object();

    public static String getAccessToken(){
        String turl = String.format("%sgrant_type=client_credential&appid=%s&secret=%s",
                PropertiesUtil.getAppConfig("weixin.req.url.token"),
                PropertiesUtil.getAppConfig("weixin.app.id"),
                PropertiesUtil.getAppConfig("weixin.app.secret"));
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(turl);
        //JSONParser jsonparer = new JSONParser();// 初始化解析json格式的对象
        String result = null;
        try {
            HttpResponse res = client.execute(get);
            String responseContent = null; // 响应内容
            HttpEntity entity = res.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
            System.out.println(responseContent);
            /*JsonObject json = jsonparer.parse(responseContent).getAsJsonObject();
            // 将json字符串转换为json对象
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                if (json.get("errcode") != null) {// 错误时微信会返回错误码等信息，{"errcode":40013,"errmsg":"invalid
                    // appid"}
                } else {// 正常情况下{"access_token":"ACCESS_TOKEN","expires_in":7200}
                    result = json.get("access_token").getAsString();
                }
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭连接 ,释放资源
            client.getConnectionManager().shutdown();
            return result;
        }
    }
}
