package com.droiddevsa.doganator.BroadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

import com.droiddevsa.doganator.services.JsonDogDownloadService;
import com.droiddevsa.doganator.utils.AppExecutors;
import com.droiddevsa.doganator.utils.DownloadSharedPreference;
import com.droiddevsa.doganator.utils.JSONDownloaderkUtils;
import com.droiddevsa.doganator.views.SplashScreen;

public class NetworkChangeListener extends BroadcastReceiver {
    private final String LOG_TAG = "NetworkChangeListener";
    @Override
    public void onReceive(final Context context, Intent intent) {
        if(ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){
            AppExecutors.getInstance().networkIO().execute(new Runnable() {
                @Override
                public void run() {
                    boolean connectionAvail = JSONDownloaderkUtils.connectionAvailable(context);
                    if(connectionAvail) {
                        Log.i(LOG_TAG,"User has reconnected");
                        startDownloadIntentService(context);
                    }
                    else
                        Log.e(LOG_TAG,"User has disconnected");
                }
            });

        }
    }

    public void startDownloadIntentService(Context context){
        if(DownloadSharedPreference.dogEntryDownloadsComplete()){
            Log.e(LOG_TAG,"Cannot start download service: Downloads are complete");
            return;
        }

        Intent downloadService = new Intent(context,JsonDogDownloadService.class);
        context.startService(downloadService);
    }
}
