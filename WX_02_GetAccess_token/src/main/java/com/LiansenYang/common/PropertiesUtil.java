package com.LiansenYang.common;

import java.util.PropertyResourceBundle;

/**
 * @description: 获取Properties
 * @author: yangLs
 * @create: 2018-03-08 15:49
 **/
public class PropertiesUtil {
    private static PropertyResourceBundle appConfigBundle;


    static {
        appConfigBundle = (PropertyResourceBundle) PropertyResourceBundle.getBundle(Constants.COMM_CONFIG_FILE);
    }

    public static String getAppConfig(String key) {
        return appConfigBundle.getString(key);
    }


}
