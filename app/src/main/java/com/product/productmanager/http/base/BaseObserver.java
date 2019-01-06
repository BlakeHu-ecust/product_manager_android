package com.product.productmanager.http.base;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.util.Log;

import com.product.productmanager.LoginActivity;
import com.product.productmanager.Other.Singleton;
import com.product.productmanager.Other.ToolClass;
import com.product.productmanager.http.ProgressDialog;
import com.product.productmanager.http.bean.BaseEntity;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static com.product.productmanager.http.InterceptorUtil.TAG;

public abstract class BaseObserver<T> implements Observer<BaseEntity<T>> {
    protected Context mContext;

    public BaseObserver(Context cxt) {
        this.mContext = cxt;
    }

    public BaseObserver() {

    }

    @Override
    public void onSubscribe(Disposable d) {
        onRequestStart();

    }

    @Override
    public void onNext(BaseEntity<T> tBaseEntity) {
        onRequestEnd();
        if (tBaseEntity.isSuccess()) {
            try {
                onSuccees(tBaseEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                onCodeError(tBaseEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        //Log.w(TAG, "onError: ", e.getCause());这里可以打印错误信息
        onRequestEnd();
        try {
            if (e instanceof ConnectException
                    || e instanceof TimeoutException
                    || e instanceof NetworkErrorException
                    || e instanceof UnknownHostException) {
                onFailure(e, true);
            } else {
                onFailure(e, false);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void onComplete() {
    }

    /**
     * 返回成功
     *
     * @param t
     * @throws Exception
     */
    protected abstract void onSuccees(BaseEntity<T> t) throws Exception;

    /**
     * 返回成功了,但是code错误
     *
     * @param t
     * @throws Exception
     */
    protected void onCodeError(BaseEntity<T> t) throws Exception{
        ToolClass.progressDismisss();
        ToolClass.showMessage(t.getMes(),Singleton.instance.getContext());
    }

    /**
     * 返回失败
     *
     * @param e
     * @param isNetWorkError 是否是网络错误
     * @throws Exception
     */
    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception{
        ToolClass.progressDismisss();
        ToolClass.showMessage(e.getLocalizedMessage(),Singleton.instance.getContext());
    }

    protected void onRequestStart() {
    }

    protected void onRequestEnd() {
        closeProgressDialog();
    }

    public void showProgressDialog() {
        ProgressDialog.show(mContext, false, "请稍后");
    }

    public void closeProgressDialog() {
        ProgressDialog.cancle();
    }

}
