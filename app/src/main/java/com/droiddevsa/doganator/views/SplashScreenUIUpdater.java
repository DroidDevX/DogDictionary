package com.droiddevsa.doganator.views;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.droiddevsa.doganator.R;
import com.droiddevsa.doganator.animation.AnimationUtils;
import com.droiddevsa.doganator.utils.DownloadSharedPreference;

import java.util.Locale;


public class SplashScreenUIUpdater {
    final static String LOG_TAG = SplashScreenUIUpdater.class.getSimpleName();

    private State_HasInternetConnection m_connectedState = new State_HasInternetConnection();
    private State_HasNoInternetConnection m_disconnectedState = new State_HasNoInternetConnection();
    private InternetConnectedState m_currentState;
    SplashScreenViewHolder m_viewHolder;

    /**
     * This class is responsible for updating the user interface of the Splash screen Activity
     * You can use the updateUserInterface() method,
     * to update the UI based on whether or not the Android device is connected
     * to the internet. The SplashScreenViewHolder memberm maintains a reference
     * to the SplashScreenViewHolder that stores references of all UI widgets.
     * */
    public SplashScreenUIUpdater(SplashScreenViewHolder viewHolder){
        Log.d(LOG_TAG,"SplashScreenUIUpdater()");
        this.m_viewHolder = viewHolder;
        m_currentState =m_disconnectedState;
    }

    /**
     * Updates the SplashScreen Activity's User Interface based on the
     * current connection state of the Android Device
     */
    public void updateUserInterface(){
        Log.d(LOG_TAG,"updateUserInterface()");
        m_currentState.updateUserInterface(m_viewHolder);
    }

    public void setConnectedState(boolean isConnectedToInternet){
        Log.e(LOG_TAG,"setConnectedState()");
        if(isConnectedToInternet)
            m_currentState = m_connectedState;
        else
            m_currentState = m_disconnectedState;
    }

    /*------------------------------------------------------------------------*/
    static public class SplashScreenViewHolder{
        private ProgressBar m_progressBar;
        private TextView m_progressTextView;
        private TextView m_splashScreenTitle;
        private ImageView m_splashImageView;
        public SplashScreenViewHolder(View rootView){
            m_progressBar       = rootView.findViewById(R.id.progressBar);
            m_progressTextView  = rootView.findViewById(R.id.progressTextView);
            m_splashScreenTitle = rootView.findViewById(R.id.splashScreenTextView);
            m_splashImageView   = rootView.findViewById(R.id.splashImageView);
        }
    }

    /*------------------------------------------------------------------------*/
    private interface InternetConnectedState{
        void updateUserInterface(SplashScreenViewHolder viewHolder);
    }

    class State_HasInternetConnection implements InternetConnectedState{
        @Override
        public void updateUserInterface(SplashScreenViewHolder viewHolder)
        {
            Log.d(LOG_TAG,"updateUserInterface(), state connected");
            //Update titles
            viewHolder.m_splashImageView.setImageResource(R.mipmap.ic_launcher_fore);
            viewHolder.m_splashScreenTitle.setText("Downloading Resources...");
            //Update Progress bar
            viewHolder.m_progressBar.setVisibility(View.VISIBLE);
            viewHolder.m_progressBar.setProgress(0);
            viewHolder.m_progressBar.setMax(DownloadSharedPreference.finalDogDownloadID());
            viewHolder.m_progressBar.setProgress(DownloadSharedPreference.currentDogDownloadID());
            viewHolder.m_progressTextView.setText(
                    String.format(Locale.ENGLISH, "Downloading %d/%d entries...", DownloadSharedPreference.currentDogDownloadID(),DownloadSharedPreference.finalDogDownloadID()));
        }
    }

    class State_HasNoInternetConnection implements InternetConnectedState{
        @Override
        public void updateUserInterface(SplashScreenViewHolder viewHolder) {
            Log.d(LOG_TAG,"updateUserInterface(), state disconnected");
            viewHolder.m_splashImageView.setImageResource(R.drawable.ic_no_connection);
            viewHolder.m_splashScreenTitle.setText("No Internet Connection Available");
            viewHolder.m_progressTextView.setText("Unable to download resources...");
            viewHolder.m_progressBar.setVisibility(View.GONE);
        }
    }


}
