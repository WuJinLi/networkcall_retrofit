package com.networkcall.wujinli.networkcall.utils;

/**
 * author: WuJinLi
 * time  : 17/6/21
 * desc  :非网络层异常处理
 */
public class Exceptions {
    public static void illegalArgument(String msg, Object... params) {
        throw new IllegalArgumentException(String.format(msg, params));
    }


}
