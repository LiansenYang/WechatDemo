package com.LiansenYang.Servlet;

import com.LiansenYang.WXUtil.CommonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="WxOpenDev",urlPatterns="/WechatServlet")
public class WxOpenDevServlet extends HttpServlet {

    //token的值是微信中自己填写的，这里的值要和微信中填写的token值一致
    private String token="123456红薯淀粉告诉对方";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        if(CommonUtil.checkSignature(signature,timestamp,nonce,token)){
            response.getWriter().append(echostr);
        }

        System.out.println("signature:"+signature+";timestamp:"+timestamp+";nonce:"+nonce+";echostr:"+echostr+";token:"+token);

    }


}
