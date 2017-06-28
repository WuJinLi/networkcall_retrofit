package com.networkcall.wujinli.networkcall.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * author: WuJinLi
 * time  : 17/6/21
 * desc  :
 */

public class PropertiesUtil {
    /**
     *
     * getAssetsString:(获取用户服务协议).
     *
     * @param context
     * @param fileName
     * @return
     * @since JDK 1.6
     */
    public static String getAssetsString(Context context, String fileName) {
        StringBuffer sb = new StringBuffer();
        try {
            AssetManager am = context.getAssets();
            InputStream in = am.open(fileName);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                line += ("\n");
                sb.append(line);
            }
            reader.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     *
     * get:(通过银行简称获取银行名称).
     *
     * @param context
     * @param string
     * @return
     * @since JDK 1.6
     */
    public static String get(Context context, String string) {
        Properties prop = new Properties();
        InputStream fis;
        try {
            fis = context.getAssets().open(Config.CONFIG_BANK_PROPERTIES);
            prop.load(fis);// 将属性文件流装载到Properties对象中
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop.getProperty(string);
    }

    /**
     * 获取asset目录下的配置文件信息
     * @param propertity
     * @return
     */
    public static String getConfigProperties(String propertity) {
        InputStream in = null;
        if(BuildConfig.BUILD_TYPE.equals("debug")){
            in = Config.class.getResourceAsStream(Config.CONFIG_PROPERTIES_DEBUG);
        } else if(BuildConfig.BUILD_TYPE.equals("release")){
            in = Config.class.getResourceAsStream(Config.CONFIG_PROPERTIES);
        } else if(BuildConfig.BUILD_TYPE.equals("release_beta")){
            in = Config.class.getResourceAsStream(Config.CONFIG_PROPERTIES_BETA);
        } else if(BuildConfig.BUILD_TYPE.equals("sandbox")){
            in = Config.class.getResourceAsStream(Config.CONFIG_PROPERTIES_SANDBOX);
        } else {
            in = Config.class.getResourceAsStream(Config.CONFIG_PROPERTIES_DEV);
        }

        Properties p = new Properties();
        //修改安全监测,讲null改成空字符串,update by CentMeng
        String value = "";
        try {
            p.load(in);
            value = p.getProperty(propertity);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (!TextUtils.isEmpty(value)){
            value = value.trim();
        }
        return value;
    }

    //是否线上环境
    public static boolean isReleaseMode(){
        return BuildConfig.BUILD_TYPE.equals("release");
    }
}
