package com.networkcall.wujinli.networkcall.callback;

/**
 * author: WuJinLi
 * time  : 17/6/21
 * desc  : 网络回调
 */
public abstract class CallBack {

    public void onStart() {
    }

    public void onCompleted() {
    }

    abstract public void onError(Throwable e);

    public void onProgress(long fileSizeDownloaded) {
    }

    abstract public void onSucess(String path, String name, long fileSize);
}
