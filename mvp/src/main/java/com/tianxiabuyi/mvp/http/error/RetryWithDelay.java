package com.tianxiabuyi.mvp.http.error;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created in 2017/9/21 16:18.
 *
 * @author Wang YaoDong.
 */
public class RetryWithDelay implements Function<Observable<Throwable>, ObservableSource<?>> {

    public final String TAG = this.getClass().getSimpleName();

    // 重试次数
    private final int maxRetries;
    // 重试间隔
    private final int retryDelaySecond;
    // 当前次数
    private int retryCount;

    public RetryWithDelay(int maxRetries, int retryDelaySecond) {
        this.maxRetries = maxRetries;
        this.retryDelaySecond = retryDelaySecond;
    }

    /**
     * When this Observable calls onNext, the original Observable will be retried (i.e. re-subscribed).
     */
    @Override
    public ObservableSource<?> apply(@NonNull Observable<Throwable> throwableObservable) throws Exception {

        return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(@NonNull Throwable throwable) throws Exception {
                if (++retryCount <= maxRetries) {
                    Log.d(TAG, "get error, it will try after " + retryDelaySecond + " second, "
                            + "retry count " + retryCount);
                    return Observable.timer(retryDelaySecond, TimeUnit.SECONDS);
                }
                // Max retries hit. Just pass the error along.
                return Observable.error(throwable);
            }
        });
    }
}