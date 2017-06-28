package com.networkcall.wujinli.networkcall.mvp.bean;


import android.content.Context;


/**
 * author: WuJinLi
 * time  : 17/6/21
 * desc  : 请求头部信息工具类，便于代码的复用，减少代码的冗余
 */
public class RequestHeaderUtils {

    public static RequestHeader getHeaderRequest(Context context) {
        RequestHeader header = new RequestHeader();
        /**
         *此处这是关于请求头部的统一信息
         */
        return header;
    }

}
