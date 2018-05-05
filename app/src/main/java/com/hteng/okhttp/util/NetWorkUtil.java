package com.hteng.okhttp.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by jimmyzhang on 2018/5/5.
 */

public class NetWorkUtil {

    private static final String TAG = NetWorkUtil.class.getSimpleName();
    private static final String CACHE_FILE_NAME = "okhttp_cache";
    private static OkHttpClient sClient;
    private static Cache sCache;
    private static final long TIME_OUT_SECOND = 15;
    private static final long OK_HTTP_CACHE_SIZE = 1024 * 1024 * 40;

    public static void init(Context context, Interceptor... interceptors) {
        if (sCache == null) {
            sCache = new Cache(context.getDir(CACHE_FILE_NAME, Context.MODE_PRIVATE),
                    OK_HTTP_CACHE_SIZE);
        }
        sClient = getOkHttpClient(sCache, interceptors);
    }

    private static OkHttpClient getOkHttpClient(Cache cache, Interceptor... interceptors) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (interceptors != null) {
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }
        builder.cache(cache).connectTimeout(TIME_OUT_SECOND, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT_SECOND, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT_SECOND, TimeUnit.SECONDS);
        return builder.build();
    }

    private static Response getResponse(Request request) throws IOException {
        return sClient.newCall(request).execute();
    }

    public static String get(String requestUrl) {
        Response response = null;
        Request request = null;
        String result = null;
        try {
            request = new Request.Builder().url(requestUrl).build();
            response = getResponse(request);
            if (response.isSuccessful()) {
                result = response.body().string();
                if (!TextUtils.isEmpty(result)) {
                    Log.d(TAG, " get request succ data : " + result);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.body().close();
                response = null;
            }
        }
        return result;
    }

    public static String post(String requestUrl, String text) {
        Response response = null;
        String result = null;
        Request request = null;
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("text/plain"), text);
            request = new Request.Builder().url(requestUrl).post(requestBody).build();
            response = getResponse(request);
            if (response.isSuccessful()) {
                result = response.body().string();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.body().close();
                response = null;
            }
        }
        return result;
    }
}
