package com.networkcall.wujinli.networkcall.utils;

import android.os.Environment;

import java.io.File;

/**
 * author: WuJinLi
 * time  : 17/6/21
 * desc  :路径和相关变量的定义（包名，生产环境，测试环境，集成环境）
 */

public class Config {
    public static String CONFIG_PACKAGE_NAME = "";//项目包名称
    public static String CONFIG_NO_NET_URL = "";// 加载没网
    public static String CONFIG_VER_FILE = "";
    public static String CONFIG_PROPERTIES = "/assets/config.properties";
    public static String CONFIG_PROPERTIES_BETA = "/assets/config_beta.properties";//内测环境路径
    public static String CONFIG_PROPERTIES_DEV = "/assets/config_dev.properties";
    public static String CONFIG_PROPERTIES_DEBUG = "/assets/config_debug.properties";
    public static String CONFIG_PROPERTIES_SANDBOX = "/assets/config_sandbox.properties";
    public static String CONFIG_BANK_PROPERTIES = "banks.properties";
    //供电窗
    public static String CONFIG_URL_WINDOWSUPPLY_BASE = "CONFIG_URL_WINDOWSUPPLY_BASE";
    //供电窗图片
    public static String CONFIG_URL_WINDOWSUPPLY_IMAGE_BASE = "CONFIG_URL_WINDOWSUPPLY_IMAGE_BASE";

    public static File CONFIG_FILE_SDCARD = Environment.getExternalStorageDirectory();// 获取SD卡的根目录


}
