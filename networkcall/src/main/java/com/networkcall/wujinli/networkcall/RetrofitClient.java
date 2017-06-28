package com.networkcall.wujinli.networkcall;

import android.content.Context;
import android.util.Log;


import com.networkcall.wujinli.networkcall.callback.CallBack;
import com.networkcall.wujinli.networkcall.constant.CacheConstant;
import com.networkcall.wujinli.networkcall.constant.TimeConstant;
import com.networkcall.wujinli.networkcall.cookie.CookieJarImpl;
import com.networkcall.wujinli.networkcall.cookie.store.PersistentCookieStore;
import com.networkcall.wujinli.networkcall.download.DownloadManager;
import com.networkcall.wujinli.networkcall.https.HttpsUtils;
import com.networkcall.wujinli.networkcall.interceptor.LoggerInterceptor;
import com.networkcall.wujinli.networkcall.log.LogUtils;
import com.networkcall.wujinli.networkcall.mvp.bean.ApiResponse;
import com.networkcall.wujinli.networkcall.mvp.biz.IBaseBiz;
import com.networkcall.wujinli.networkcall.utils.ExceptionHandle;
import com.networkcall.wujinli.networkcall.utils.PropertiesUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * author: WuJinLi
 * time  : 17/6/21
 * desc  :RetrofitClient网络访问工具类
 */

public class RetrofitClient {
    private IBaseBiz baseBiz;
    private OkHttpClient okHttpClient;
    public static String BASE_URL = "https://www.baidu.com/";
    private Context mContext;
    private Retrofit retrofit;

    private static RetrofitClient instance;

    /**
     * 使用baseUrl
     *
     * @param context
     * @return
     */
    public static RetrofitClient getInstance(Context context) {
        synchronized (RetrofitClient.class) {
            if (instance == null) {
                instance = new RetrofitClient(context);
            }
        }
        return instance;
    }


    private RetrofitClient(Context context) {
        if (context != null) {
            mContext = context;
        }
        synchronized (OkHttpClient.class) {
            if (okHttpClient == null) {
                try {
                    LogUtils.e("msj", "创建okhttpClient");
                    if (PropertiesUtil.isReleaseMode()) {
                        /**
                         * 正式环境需要加载证书，则通过流的方式进行加载
                         */
                        InputStream[] inputStreams = new InputStream[1];
                        inputStreams[0] = context.getAssets().open("key.cer");
                        InputStream inputStream = context.getAssets().open("keystore.bks");
                        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory
                                (inputStreams, inputStream, "pw123456");
                        /**
                         * 设置网络访问属性
                         */
                        okHttpClient = new OkHttpClient().newBuilder()
                                //添加log拦截器用来打印log
                                .addInterceptor(new LoggerInterceptor(LoggerInterceptor.TAG, false))
                                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams
                                        .trustManager)
                                .cookieJar(new CookieJarImpl(new PersistentCookieStore(context)))
                                //添加header
//                                .addInterceptor(new HeaderInterceptor(headers))
                                //必须是设置Cache目录
                                .cache(new Cache(CacheConstant.getDiskCacheDir(context,
                                        CacheConstant.CACHE_FOLDER), CacheConstant.CACHE_SIZE))
                                //走缓存，两个都要设置
                                .connectTimeout(TimeConstant.TIMEOUT_CONNECTION, TimeUnit.SECONDS)
                                //失败重连
                                .retryOnConnectionFailure(true)
                                //time out
                                .readTimeout(TimeConstant.TIMEOUT_READ, TimeUnit.SECONDS)//读取超时时间
                                .writeTimeout(TimeConstant.TIMEOUT_CONNECTION, TimeUnit.SECONDS)
                                //写入的超时时间
                                .connectionPool(new ConnectionPool(8, 15, TimeUnit.SECONDS))//连接池
                                .proxy(Proxy.NO_PROXY)//避免接口的抓取
                                //添加UA
//                    .addInterceptor(new UserAgentInterceptor(HttpHelper.getUserAgent()))
                                .build();
                    } else {
                        /**
                         * 没有证书加载
                         */
                        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null,
                                null, null);
                        okHttpClient = new OkHttpClient().newBuilder()
                                //添加log拦截器用来打印log
                                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams
                                        .trustManager)
                                .hostnameVerifier(new UnSafeHostnameVerifier())

                                .addInterceptor(new LoggerInterceptor(LoggerInterceptor.TAG, true))
                                .cookieJar(new CookieJarImpl(new PersistentCookieStore(context)))
                                //添加header
//                                .addInterceptor(new HeaderInterceptor(headers))
                                //必须是设置Cache目录
                                .cache(new Cache(CacheConstant.getDiskCacheDir(context,
                                        CacheConstant.CACHE_FOLDER), CacheConstant.CACHE_SIZE))
                                //走缓存，两个都要设置
                                .connectTimeout(TimeConstant.TIMEOUT_CONNECTION, TimeUnit.SECONDS)
                                //失败重连
                                .retryOnConnectionFailure(true)
                                //time out
                                .readTimeout(TimeConstant.TIMEOUT_READ, TimeUnit.SECONDS)
                                .writeTimeout(TimeConstant.TIMEOUT_CONNECTION, TimeUnit.SECONDS)
                                .connectionPool(new ConnectionPool(8, 15, TimeUnit.SECONDS))
                                //添加UA
//                    .addInterceptor(new UserAgentInterceptor(HttpHelper.getUserAgent()))
                                .build();
                    }

                } catch (IOException e) {
                    Log.e("msj", "证书读取异常");
                    e.printStackTrace();
                }
            } else {
                LogUtils.e("msj", "没有创建okhttpClient");
            }
        }

        synchronized (Retrofit.class) {
            if (retrofit == null) {
                LogUtils.e("msj", "创建retrofit");
                retrofit = new Retrofit.Builder()
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .baseUrl(BASE_URL)
                        .build();
            } else {
                LogUtils.e("msj", "没有创建retrofit");
            }
        }


    }

    /**
     * create BaseApi  defalte ApiManager
     *
     * @return ApiManager
     */
    public RetrofitClient createBaseApi() {
        baseBiz = create(IBaseBiz.class);
        return this;
    }

    /**
     * create you ApiService
     * Create an implementation of the API endpoints defined by the {@code service} interface.
     */
    public <T> T create(final Class<T> service) {
        if (service == null) {
            throw new RuntimeException("Api service is null!");
        }
        return retrofit.create(service);
    }

    public void upload(String url, RequestBody requestBody, Subscriber<ResponseBody> subscriber) {
        baseBiz.upLoadFile(url, requestBody)
                .compose(schedulersTransformer())
                .compose(transformer())
                .subscribe(subscriber);
    }

    public void download(String url, final CallBack callBack) {
        baseBiz.downloadFile(url)
                .compose(schedulersTransformer())
                .compose(transformer())
                .subscribe(new DownSubscriber<ResponseBody>(callBack));
    }

    Observable.Transformer schedulersTransformer() {
        return new Observable.Transformer() {


            @Override
            public Object call(Object observable) {
                return ((Observable) observable).subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }

           /* @Override
            public Observable call(Observable observable) {
                return observable.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }*/
        };
    }

    <T> Observable.Transformer<T, T> applySchedulers() {
        return (Observable.Transformer<T, T>) schedulersTransformer();
    }

    public <T> Observable.Transformer<ApiResponse<T>, T> transformer() {

        return new Observable.Transformer() {

            @Override
            public Object call(Object observable) {
                return ((Observable) observable).map(new HandleFuc<T>()).onErrorResumeNext(new
                        HttpResponseFunc<T>());
            }
        };
    }

    public <T> Observable<T> switchSchedulers(Observable<T> observable) {
        return observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private static class HttpResponseFunc<T> implements Func1<Throwable, Observable<T>> {
        @Override
        public Observable<T> call(Throwable t) {
            return Observable.error(ExceptionHandle.handleException(t));
        }
    }

    /**
     * 做些成功和错误公共处理
     *
     * @param <T>
     */
    private class HandleFuc<T> implements Func1<ApiResponse<T>, T> {
        @Override
        public T call(ApiResponse<T> response) {
            if (!response.isSuccess())
                throw new RuntimeException(response.getHeader().getRepcd() + "" + response
                        .getHeader().getRepmsg() != null ? response.getHeader().getRepmsg() : "");
            return response.getData();
        }
    }


    /**
     * /**
     * execute your customer API
     * For example:
     * MyApiService service =
     * RetrofitClient.getInstance(MainActivity.this).create(MyApiService.class);
     * <p>
     * RetrofitClient.getInstance(MainActivity.this)
     * .execute(service.lgon("name", "password"), subscriber)
     * * @param subscriber
     */

    public static <T> T execute(Observable<T> observable, Observer<T> subscriber) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

        return null;
    }


    /**
     * DownSubscriber
     *
     * @param <ResponseBody>
     */
    class DownSubscriber<ResponseBody> extends Subscriber<ResponseBody> {
        CallBack callBack;

        public DownSubscriber(CallBack callBack) {
            this.callBack = callBack;
        }

        @Override
        public void onStart() {
            super.onStart();
            if (callBack != null) {
                callBack.onStart();
            }
        }

        @Override
        public void onCompleted() {
            if (callBack != null) {
                callBack.onCompleted();
            }
        }

        @Override
        public void onError(Throwable e) {
            if (callBack != null) {
                callBack.onError(e);
            }
        }

        @Override
        public void onNext(ResponseBody responseBody) {
            DownloadManager.getInstance(callBack).writeResponseBodyToDisk(mContext,
                    (okhttp3.ResponseBody) responseBody);

        }
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    private class UnSafeHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
}
