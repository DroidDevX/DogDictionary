package com.droiddevsa.doganator.data;

import android.util.Log;

public class TestStatic {
    public static final String LOG_TAG= TestStatic.class.getSimpleName();
    public static String DATA="NOT MODIFIED";

    public static void setDATA(String className,String newData){
        Log.d(LOG_TAG,"Old data: "+ DATA);
        DATA = newData;
        Log.d(LOG_TAG,"Class Name: "+ className + ", New data: "+ newData);

    }


}
