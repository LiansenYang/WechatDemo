package com.LiansenYang.WX.custromMenu;

import com.LiansenYang.common.PropertiesUtil;
import com.LiansenYang.common.WXUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;

/**
 * @description: 创建自定义菜单
 * @author: yangLs
 * @create: 2018-03-09 10:51
 **/
public class Menu {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String turl = String.format("https://api.weixin.qq.com/cgi-bin/menu/create?access_token=%s",WXUtil.getInstance().getWeixinParameterMapFromCache().get("access_token"));
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(turl);

        StringBuffer menuData = new StringBuffer();
        String domain = "f3rj8s.natappfree.cc";
        menuData.append("{                                                                                      ");
        menuData.append("     \"button\":[                                                                      ");
        menuData.append("     {	                                                                                ");
        menuData.append("          \"type\":\"click\",                                                          ");
        menuData.append("          \"name\":\"今日歌曲\",                                                       ");
        menuData.append("          \"key\":\"V1001_TODAY_MUSIC\"                                                ");
        menuData.append("      },                                                                               ");
        menuData.append("      {                                                                                ");
        menuData.append("           \"name\":\"菜单\",                                                          ");
        menuData.append("           \"sub_button\":[                                                            ");
        menuData.append("           {	                                                                        ");
        menuData.append("               \"type\":\"view\",                                                      ");
        menuData.append("               \"name\":\"搜索\",                                                      ");
        menuData.append("               \"url\":\"http://"+domain+"/TestServlet\"                                        ");
        menuData.append("            },                                                                         ");
        menuData.append("           {	                                                                        ");
        menuData.append("               \"type\":\"view\",                                                      ");
        menuData.append("               \"name\":\"微信网页授权\",                                                      ");
        StringBuilder url = new StringBuilder();
        String rediret_url = URLEncoder.encode("http://"+domain+"/OAuth2Servlet","UTF-8");
        String rediret_url2 = URLEncoder.encode("http://"+domain+"/TestJssdkServlet","UTF-8");
        url.append("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx9aa2a8970152d16d&redirect_uri="+rediret_url+"&response_type=code&scope=snsapi_base&state=123");
        menuData.append("               \"url\":\""+url.toString()+"\"                                        ");
        menuData.append("            },                                                                         ");

        menuData.append("           {	                                                                        ");
        menuData.append("               \"type\":\"view\",                                                      ");
        menuData.append("               \"name\":\"TestJssdkServlet\",                                                      ");
        StringBuilder url2 = new StringBuilder();
        url2.append("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx9aa2a8970152d16d&redirect_uri="+rediret_url2+"&response_type=code&scope=snsapi_base&state=123");
        menuData.append("               \"url\":\""+url2.toString()+"\"                                        ");
        menuData.append("            },                                                                         ");


        menuData.append("           {	                                                                        ");
        menuData.append("               \"type\":\"view\",                                                      ");
        menuData.append("               \"name\":\"ImageServlet\",                                                      ");
        StringBuilder url4 = new StringBuilder();
        String rediret_url3 = URLEncoder.encode("http://"+domain+"/ImageServlet","UTF-8");
        url4.append("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx9aa2a8970152d16d&redirect_uri="+rediret_url3+"&response_type=code&scope=snsapi_userinfo&state=123");
        menuData.append("               \"url\":\""+url4.toString()+"\"                                        ");
        menuData.append("            },                                                                         ");

        menuData.append("           {	                                                                        ");
        menuData.append("               \"type\":\"view\",                                                      ");
        menuData.append("               \"name\":\"微信网页需授权\",                                                      ");
        StringBuilder url3 = new StringBuilder();
        url3.append("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx9aa2a8970152d16d&redirect_uri="+rediret_url+"&response_type=code&scope=snsapi_userinfo&state=123");
        menuData.append("               \"url\":\""+url3.toString()+"\"                                        ");
        menuData.append("            }]                                                                         ");
        menuData.append("       }]                                                                              ");
        menuData.append(" }                                                                                     ");

        StringEntity content = new StringEntity(menuData.toString(), Charset.forName("UTF-8"));// 第二个参数，设置后才会对，内容进行编码
        content.setContentType("application/json; charset=UTF-8");
        content.setContentEncoding("UTF-8");
        httppost.setEntity(content);

        HttpResponse response = null;

        String sentity = null;
        try {
            response = httpclient.execute(httppost);
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                sentity = EntityUtils.toString(resEntity, "UTF-8");
                System.out.println(sentity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源


        }
    }
}
