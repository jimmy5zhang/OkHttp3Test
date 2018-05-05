package com.hteng.okhttp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hteng.okhttp.R;
import com.hteng.okhttp.util.NetWorkUtil;

public class MainActivity extends AppCompatActivity {

    private static final String REQUEST_URL = "https://www.baidu.com";

    private void initView() {

    }

    private void initData() {

    }

    private void initListeners() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initListeners();
    }

    public void onGetClick(View view) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                NetWorkUtil.get(REQUEST_URL);
            }
        }.start();
    }
}
