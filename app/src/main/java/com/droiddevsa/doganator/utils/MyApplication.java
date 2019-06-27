package com.droiddevsa.doganator.utils;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
    private static Context m_Context;
    public void onCreate(){
        super.onCreate();
        MyApplication.m_Context = getApplicationContext();
    }

    public static Context getAppContext(){
        return MyApplication.m_Context;
    }
}
