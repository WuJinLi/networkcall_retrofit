package com.networkcall.wujinli.networkcall.interceptor;

import android.content.Context;
import android.util.Log;


import com.networkcall.wujinli.networkcall.constant.CacheConstant;
import com.networkcall.wujinli.networkcall.utils.NetworkUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * author: WuJinLi
 * time  : 17/6/21
 * desc  : 缓存拦截器
 */
public class CacheInterceptor implements Interceptor {

    private Context context;

    public CacheInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (NetworkUtils.isNetworkAvailable(context)) {
            Response response = chain.proceed(request);
            // 在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回
            int maxAge = CacheConstant.MAX_AGE;
            String cacheControl = request.cacheControl().toString();
            Log.e("msj", "60s load cahe" + cacheControl);
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Pragma", "public")
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        } else {
            Log.e("msj", " no network load cahe");
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
            Response response = chain.proceed(request);
            //set cahe times is 3 days
            int maxStale = CacheConstant.MAX_STALE;
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Pragma", "public")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
    }
}
