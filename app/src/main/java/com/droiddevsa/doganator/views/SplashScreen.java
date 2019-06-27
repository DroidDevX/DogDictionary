package com.droiddevsa.doganator.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import com.droiddevsa.doganator.BroadcastReceivers.NetworkChangeListener;
import com.droiddevsa.doganator.R;
import com.droiddevsa.doganator.animation.AnimationUtils;
import com.droiddevsa.doganator.services.JsonDogDownloadService;
import com.droiddevsa.doganator.utils.DownloadSharedPreference;
import com.droiddevsa.doganator.utils.JSONDownloaderkUtils;
import com.droiddevsa.doganator.utils.MyApplication;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SplashScreen extends AppCompatActivity {

    final static String LOG_TAG = SplashScreen.class.getSimpleName();

    //Ui
    SplashScreenUIUpdater UI_Updater;

    ScheduledExecutorService m_progressBarUpdater;
    NetworkChangeListener m_netBroadCastReceiver= new NetworkChangeListener();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(LOG_TAG,"onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        View rootView = findViewById(R.id.splashScreenRootView);
        AnimationUtils.applyFadeInOutAnimation(rootView.findViewById(R.id.splashImageView));
        SplashScreenUIUpdater.SplashScreenViewHolder viewHolder = new SplashScreenUIUpdater.SplashScreenViewHolder(rootView);
        UI_Updater = new SplashScreenUIUpdater(viewHolder);


        //Initialize broadcast receiver to react to WiFi connection
        //(Restarts Intent Download service, if app recovers from an Internet disconnection)
        setConnectedToWiFi_BroadCastReceiver();

       //Check for download updates every two seconds, and update UI Progressbar
        int interval =2;//2s
        checkForUpdateAtInterval(interval);

        //In the mean time, download data in the background
        startDownloadIntentService();

    }


    public void checkForUpdateAtInterval(int interval){
        m_progressBarUpdater = Executors.newSingleThreadScheduledExecutor();
        m_progressBarUpdater.scheduleAtFixedRate (
                new checkForUpdatesRunnable(),0, interval, TimeUnit.SECONDS);
    }

    public class checkForUpdatesRunnable implements  Runnable{
        @Override
        public void run() {
            //1) Check if there is internet connection
            final boolean connectedToInternet = JSONDownloaderkUtils.connectionAvailable(MyApplication.getAppContext());

            // 2) Update UI accordingly based connection state
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    UI_Updater.setConnectedState(connectedToInternet);
                    UI_Updater.updateUserInterface();
                }
            });
            //3) Check if downloads have been completed, if completed start main activity
            if(DownloadSharedPreference.dogEntryDownloadsComplete())
            {
                startMainActivity();
                m_progressBarUpdater.shutdown();
            }
        }
    }


    public void startDownloadIntentService(){
        if(DownloadSharedPreference.dogEntryDownloadsComplete()){
            Log.e(LOG_TAG,"Cannot start download service: Downloads are complete");
            return;
        }

        Intent downloadService = new Intent(this,JsonDogDownloadService.class);
        startService(downloadService);
    }
    public void startMainActivity(){
        finish();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);

    }
    public void setConnectedToWiFi_BroadCastReceiver(){
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(m_netBroadCastReceiver,filter);
    }
    public void removeNetworkBroadCastReceiver(){
        unregisterReceiver(m_netBroadCastReceiver);
    }
    @Override
    protected void onDestroy() {
        m_progressBarUpdater.shutdown();
        removeNetworkBroadCastReceiver();
        super.onDestroy();

    }
}
