package com.droiddevsa.doganator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.droiddevsa.doganator.Utils.DogApiNetworkUtils;
import com.droiddevsa.doganator.data.DogEntry;
import com.droiddevsa.doganator.data.DogViewModel;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final static String LOG_TAG = MainActivity.class.getSimpleName();
    public static int DOG_INDEX=-1;
    public static String DOG_INDEX_KEY="dogIndex";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //View model
        DogViewModel m_viewModel = ViewModelProviders.of(this).get(DogViewModel.class);
        m_viewModel.liveData().observe(this, new Observer<List<DogEntry>>() {
            @Override
            public void onChanged(List<DogEntry> dogEntries) {
                updateUi(dogEntries);
            }
        });


        //TODO 1) - Test if Url builds properly
        String URL = DogApiNetworkUtils.buildImagesUri().toString();
        if (!URL.isEmpty())
            Log.i(LOG_TAG, "URL: " + URL);
        else
            Log.i(LOG_TAG, "URL is null/ invalid");

        //TODO 2) - Test if threads coded properly - app doesnt crash
        //TODO 3) - Check if you get a respconse from a server

        Log.i(LOG_TAG,"Thread started");
        fetchDogDataTask data = new fetchDogDataTask();
        data.execute(DogApiNetworkUtils.buildImagesUri());


    }
    class fetchDogDataTask extends AsyncTask<URL,Void,Void>{
        @Override
        protected void onPreExecute() {
            Log.i(LOG_TAG,"onPreExecute()");
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(URL... urls) {
            Log.i(LOG_TAG,"doInBackground()");
            URL url = urls[0];
            try {
                Log.i(LOG_TAG,"Response :"+ DogApiNetworkUtils.getResponse(url));

            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.i(LOG_TAG,"onPostExecute()");
        }
    }



    public void onFABclicked(View FAB){
        Intent i = new Intent(this,AddDataActivity.class);
        startActivity(i);
    }

    public void updateUi(List<DogEntry> dogEntries)
    {
        Log.i(LOG_TAG,"Dog Entries: "+ dogEntries.toString());
    }



}
