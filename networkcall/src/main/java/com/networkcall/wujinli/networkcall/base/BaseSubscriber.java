package com.networkcall.wujinli.networkcall.base;

import android.content.Context;


import com.networkcall.wujinli.networkcall.log.LogUtils;
import com.networkcall.wujinli.networkcall.utils.ExceptionHandle;
import com.networkcall.wujinli.networkcall.utils.NetworkUtils;

import rx.Subscriber;

/**
 * author: WuJinLi
 * time  : 17/6/21
 * desc  : Rxjava相应错做基础订阅
 */
public abstract class BaseSubscriber<T> extends Subscriber<T> {

    private Context context;

    public BaseSubscriber(Context context) {
        this.context = context;
    }

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable e) {
//        LogUtils.e("msj", e.getMessage());

        if (e instanceof ExceptionHandle.ResponeThrowable) {
            onError((ExceptionHandle.ResponeThrowable) e);
        } else {
            ExceptionHandle.ResponeThrowable responeThrowable = ExceptionHandle.handleException(e);
            onError(responeThrowable);
        }
        //错误也要执行完毕，错误写法，onError和onCompleted()互斥，一个队列只能调取一个
    }


    @Override
    public void onStart() {
        super.onStart();
        LogUtils.e("msj", "http is start");

        // todo some common as show loadding  and check netWork is NetworkAvailable
        // if  NetworkAvailable no !   must to call onCompleted
        if (!NetworkUtils.isNetworkAvailable(context)) {
//            Toast.makeText(context, "无网络，读取缓存数据", Toast.LENGTH_SHORT).show();
            onCompleted();
        }

    }

    @Override
    public void onCompleted() {

        LogUtils.e("msj", "onComleted");
        // todo some common as  dismiss loadding
    }
}
