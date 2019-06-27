package com.droiddevsa.doganator.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class DownloadSharedPreference {
     //Stores the current state of the number of dog entries downloaded.
    final static String LOG_TAG ="DownloadSharedPref";
    private  static final String DOWNLOAD_CONFIG = "downloadConfig";
    private static final String NEXT_DOWNLOAD_ID ="next_id";
    private static final String LAST_DOWNLOAD_ID ="last_id";
    private static final String ALL_ENTRIES_DOWNLOADED="download_completion";
    private static final String DATA_IS_DOWNLOADING="is_downloading";

    public static int currentDogDownloadID(){
        Log.i(LOG_TAG,"currentDogDownloadID()");
        SharedPreferences downloadConfig = MyApplication.getAppContext().getSharedPreferences(DOWNLOAD_CONFIG,Context.MODE_PRIVATE);
        return downloadConfig.getInt(NEXT_DOWNLOAD_ID,1);
    }

    public static void incrementDownloadID(){
        Log.i(LOG_TAG,"setDownloading()");
        SharedPreferences downloadConfig = MyApplication.getAppContext().getSharedPreferences(DOWNLOAD_CONFIG,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =downloadConfig.edit();
        editor.putInt(NEXT_DOWNLOAD_ID, currentDogDownloadID()+1);
        editor.apply();
    }


    public static int finalDogDownloadID(){
         Log.i(LOG_TAG,"finalDogDownloadID()");
         SharedPreferences downloadConfig = MyApplication.getAppContext().getSharedPreferences(DOWNLOAD_CONFIG,Context.MODE_PRIVATE);
         return downloadConfig.getInt(LAST_DOWNLOAD_ID,250);
     }


    public static void setDownloading(boolean isDownloading){
        Log.i(LOG_TAG,"setDownloading()");
        SharedPreferences downloadConfig = MyApplication.getAppContext().getSharedPreferences(DOWNLOAD_CONFIG,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =downloadConfig.edit();
        editor.putBoolean(DATA_IS_DOWNLOADING,isDownloading);
        editor.apply();
    }

    public static boolean dogEntryDownloadsComplete(){
        Log.i(LOG_TAG,"dogEntryDownloadsComplete()");
        SharedPreferences downloadConfig = MyApplication.getAppContext().getSharedPreferences(DOWNLOAD_CONFIG,Context.MODE_PRIVATE);
        return downloadConfig.getBoolean(ALL_ENTRIES_DOWNLOADED,false);
    }

    public static void setDownloadComplete(){
        Log.i(LOG_TAG,"setDownloadComplete()");
        SharedPreferences downloadConfig = MyApplication.getAppContext().getSharedPreferences(DOWNLOAD_CONFIG,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =downloadConfig.edit();
        editor.putBoolean(ALL_ENTRIES_DOWNLOADED,true);
        editor.apply();
    }


}
