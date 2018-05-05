package com.hteng.okhttp;

import android.app.Application;

import com.hteng.okhttp.util.NetWorkUtil;

import okhttp3.Interceptor;

/**
 * Created by jimmyzhang on 2018/5/5.
 */

public class OkHttpApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initNetWork();
    }

    private void initNetWork() {
        NetWorkUtil.init(this, new Interceptor[]{});
    }
}
