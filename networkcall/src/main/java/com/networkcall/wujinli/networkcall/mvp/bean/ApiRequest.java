package com.networkcall.wujinli.networkcall.mvp.bean;


import android.content.Context;

import java.util.HashMap;
import java.util.Map;


/**
 * author: WuJinLi
 * time  : 17/6/21
 * desc  :  不需要传sessionId（tokenId） new ApiRequest
 * 只需要sessionId （tokenId） new CommonApiRequest
 * 有其他参数创建新Request继承ApiRequest
 */
public class ApiRequest {

    protected RequestHeader header;

    protected Map<String, String> data = new HashMap<>();

    public ApiRequest(Map<String, String> dataMap, Context context) {
        header = RequestHeaderUtils.getHeaderRequest(context);
        this.data = dataMap;
    }

    public ApiRequest(Map<String, String> dataMap, Context context, boolean addSessionId) {
        this(dataMap, context);
        if (addSessionId) {
            data.put("", "");
        }
    }

    /**
     * data中没有任何参数,包括sessionId
     *
     * @param context
     */
    public ApiRequest(Context context) {
        header = RequestHeaderUtils.getHeaderRequest(context);
    }

    /**
     * data中只有一个sessionId参数,addSessionId传true,调用CommonRequest
     *
     * @param context
     * @param addSessionId
     */
    public ApiRequest(Context context, boolean addSessionId) {
        header = RequestHeaderUtils.getHeaderRequest(context);
        if (addSessionId) {
            data.put("", "");
        }
    }

    public RequestHeader getHeader() {
        return header;
    }

    public void setHeader(RequestHeader header) {
        this.header = header;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
