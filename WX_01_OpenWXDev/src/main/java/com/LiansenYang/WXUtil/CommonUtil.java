package com.LiansenYang.WXUtil;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class CommonUtil {

    public static Boolean checkSignature(String signature,String timestamp,String nonce,String token){
        String[] strs=new String[] {token,timestamp,nonce};
        Arrays.sort(strs);
        StringBuffer content=new StringBuffer();
        for (int i = 0; i < strs.length; i++) {
            content.append(strs[i]);
        }

        String tmpStr = null;

        tmpStr = SHA1.encode(content.toString());

        System.out.println(tmpStr.toUpperCase());
        return signature!=null?signature.toUpperCase().equals(tmpStr.toUpperCase()):false;
    }
}
