package com.product.productmanager;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.product.productmanager.Other.SharedPreferencesHelper;
import com.product.productmanager.Other.Singleton;

import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BaseActivity extends FragmentActivity {
    protected SharedPreferencesHelper sharedPreferencesHelper;

    protected static final String COMPANY_NO = "enterprise";
    protected static final String USER_NAME = "userName";
    protected static final String PASSWORD = "passWord";
    protected static final Integer DEFAULT_SIZE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Singleton.instance.setContext(this);
    }

    public <T> ObservableTransformer<T,T> setThread(){
        return new ObservableTransformer<T,T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
