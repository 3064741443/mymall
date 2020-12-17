package com.james.mall.common.api;

/**
 * 封装API的错误码
 * Created by james on 2019/4/19.
 */
public interface IErrorCode {
    long getCode();

    String getMessage();
}
