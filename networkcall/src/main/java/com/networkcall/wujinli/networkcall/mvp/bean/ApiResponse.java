package com.networkcall.wujinli.networkcall.mvp.bean;

/**
 * author: WuJinLi
 * time  : 17/6/21
 * desc  : 借口返回数据（包含头部信息header和数据实体信息data）
 */
public class ApiResponse<T> extends BaseEntity {

    private ResponseHeader header;

    private T data;

    public ResponseHeader getHeader() {
        return header;
    }

    public void setHeader(ResponseHeader header) {
        this.header = header;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return "".equals(header.getRepcd());
    }
}
