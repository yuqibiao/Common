package com.afrid.common.net;

/**
 * 功能：网络请求回调
 *
 * @author yyyu
 * @version 1.0
 * @date 2017/8/10
 */

public interface IRequestCallback<T> {

    void onSuccess(T result);

    void onFailure(Throwable throwable);
}
