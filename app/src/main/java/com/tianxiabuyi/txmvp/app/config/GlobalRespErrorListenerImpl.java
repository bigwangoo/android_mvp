package com.tianxiabuyi.txmvp.app.config;

import android.content.Context;
import android.net.ParseException;

import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.tianxiabuyi.mvp.http.error.ResponseErrorListener;
import com.tianxiabuyi.mvp.utils.AppUtils;

import org.json.JSONException;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;
import timber.log.Timber;

/**
 * 全局网络请求错误监听
 * <p>
 * Created in 2017/9/22 10:31.
 *
 * @author Wang YaoDong.
 */
public class GlobalRespErrorListenerImpl implements ResponseErrorListener {

    @Override
    public void handleResponseError(Context context, Throwable t) {
        Timber.tag("Catch-Error").w(t.getMessage());

        // 这里不光是只能打印错误, 还可以根据不同的错误作出不同的逻辑处理
        String msg = "未知错误";
        if (t instanceof UnknownHostException) {
            msg = "网络不可用";
        } else if (t instanceof SocketTimeoutException) {
            msg = "请求网络超时";
        } else if (t instanceof HttpException) {
            HttpException httpException = (HttpException) t;
            // http error
            msg = convertStatusCode(httpException);
        } else if (t instanceof JsonIOException || t instanceof JsonParseException ||
                t instanceof ParseException || t instanceof JSONException) {
            msg = "数据解析错误";
        }
        AppUtils.snackbarText(msg);
    }

    /**
     * http 错误处理
     */
    private String convertStatusCode(HttpException httpException) {
        String msg;
        if (httpException.code() == 500) {
            msg = "服务器发生错误";
        } else if (httpException.code() == 404) {
            msg = "请求地址不存在";
        } else if (httpException.code() == 403) {
            msg = "请求被服务器拒绝";
        } else if (httpException.code() == 307) {
            msg = "请求被重定向到其他页面";
        } else {
            msg = httpException.message();
        }
        return msg;
    }
}
