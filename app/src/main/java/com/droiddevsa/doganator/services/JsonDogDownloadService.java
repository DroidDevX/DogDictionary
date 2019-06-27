package com.droiddevsa.doganator.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.droiddevsa.doganator.data.DogDatabase;
import com.droiddevsa.doganator.data.DogEntry;
import com.droiddevsa.doganator.utils.DownloadSharedPreference;
import com.droiddevsa.doganator.utils.JSONDownloaderkUtils;

import androidx.annotation.Nullable;

public class JsonDogDownloadService extends IntentService{
    private static final String TAG = "JsonDogDownloadService";
    public JsonDogDownloadService(){
        super("JsonDownloadService");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        //Avoid duplicate service: Stop if shared pref says already downloading
        Log.i(TAG,"onHandleIntent");

        //Abort if all records downloaded
        if(DownloadSharedPreference.dogEntryDownloadsComplete()) {
            Log.i(TAG,"Downloads have been completed. Aborting service now");
            return;
        }

        //Download the next dog entries, that come after the last entry in the database. Starting at m_startID
        DownloadSharedPreference.setDownloading(true);

        do{
            if(!JSONDownloaderkUtils.connectionAvailable(this))
            {
                Log.e(TAG,"Connection Dropped while downloading. Terminating downloading service");
                break;
            }

            DogEntry downloadedEntry = JSONDownloaderkUtils.downloadDogData(
                                                                 DownloadSharedPreference.currentDogDownloadID());


            if(downloadedEntry.isValid())
                DogDatabase.getInstance(this).dogDao().insertEntryIntoDatabase_ROOM(downloadedEntry);

            if(DownloadSharedPreference.currentDogDownloadID()< DownloadSharedPreference.finalDogDownloadID())
                DownloadSharedPreference.incrementDownloadID();

            if(DownloadSharedPreference.currentDogDownloadID()>=DownloadSharedPreference.finalDogDownloadID())
                DownloadSharedPreference.setDownloadComplete();

        }while(!DownloadSharedPreference.dogEntryDownloadsComplete());

        DownloadSharedPreference.setDownloading(false);
    }
}


/*Downloader service test checklist
- How does app deal with downloads when no connection avail or when connection suddenly drops
- How does app deal with rotation. How to prevent restarting service? * */