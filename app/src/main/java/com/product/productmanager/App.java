package com.product.productmanager;

import android.app.Application;

import com.tencent.bugly.crashreport.CrashReport;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashReport.initCrashReport(getApplicationContext(), "e0b90440f3", true);
    }
}
