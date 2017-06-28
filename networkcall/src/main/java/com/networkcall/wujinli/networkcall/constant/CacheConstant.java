package com.networkcall.wujinli.networkcall.constant;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * author: WuJinLi
 * time  : 17/6/21
 * desc  : 缓存
 */
public class CacheConstant {

    public static final int MAX_AGE = 3;

    public static final int MAX_STALE = 60 * 60 * 24 * 3;

    public static final int CACHE_SIZE = 10 * 1024 * 1024;//10MB

    public static final String CACHE_FOLDER = "request";//缓存文件夹名

    /**
     * 获取缓存路径地址
     * @param context
     * @return
     */
    public static String getDiskCachePath(Context context){
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())
                ) {
            try {
                cachePath = context.getExternalCacheDir().getPath();
            } catch (Exception e) {
                cachePath = context.getCacheDir().getPath();
            }
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;
    }

    /**
     * 创建和获取缓存路径 
     *
     * @param uniqueName
     * @return
     */
    public static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath = getDiskCachePath(context);
        File file = new File(cachePath + File.separator + uniqueName);
        file.mkdirs();
        return file;
    }

}
