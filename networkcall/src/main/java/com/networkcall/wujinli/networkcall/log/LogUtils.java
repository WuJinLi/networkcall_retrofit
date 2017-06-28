package com.networkcall.wujinli.networkcall.log;

import android.util.Log;


import com.networkcall.wujinli.networkcall.constant.LogConstant;
import com.networkcall.wujinli.networkcall.utils.BuildConfig;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Date;


/**
 * /**
 * author: WuJinLi
 * time  : 17/6/21
 * desc 日志打印
 */
public class LogUtils {
    private static final boolean LOG_MODE = BuildConfig.DEBUG;


    public static void e(String tag, String message) {
        if (LOG_MODE) {
            Log.e(tag, message);
        }
    }

    public static void w(String tag, String message) {
        if (LOG_MODE) {
            Log.w(tag, message);
        }
    }

    public static void d(String tag, String message) {
        if (LOG_MODE) {
            Log.d(tag, message);
        }
    }

    public static void i(String tag, String message) {
        if (LOG_MODE) {
            Log.i(tag, message);
        }
    }


    public static void outputLog(String directorypath, String msg) {
        File directory = new File(directorypath);
        if (!directory.isDirectory()) {
            directory.mkdirs();
        }

        Date date = new Date();
        String fileName = String.valueOf(date.getYear()) + "年"
                + String.valueOf(date.getMonth()) + "月"
                + String.valueOf(date.getDate()) + "日"
                + String.valueOf(date.getHours()) + "点"
                + String.valueOf(date.getMinutes()) + "分"
                + String.valueOf(date.getSeconds()) + "秒";
        File f = new File(directorypath + File.separator + fileName + "log.txt");
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(f, true));
            writer.append(msg + "\r\n");
            writer.flush();
            writer.close();
        } catch (Exception e) {
            Log.e(LogConstant.DATA_OUTPUT, "日志输出错误");
        }
    }


}
