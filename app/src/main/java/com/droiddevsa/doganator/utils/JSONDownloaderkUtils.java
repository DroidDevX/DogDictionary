package com.droiddevsa.doganator.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import com.droiddevsa.doganator.data.DogEntry;
import com.droiddevsa.doganator.data.UrlContract;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.annotation.Nullable;
import javax.net.ssl.HttpsURLConnection;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JSONDownloaderkUtils {
        //Description: Helper class that makes an API call to a backend server to retrieve a dog entry and insert into local ROOM db
        private static final String LOG_TAG = JSONDownloaderkUtils.class.getSimpleName();
        public static boolean DATA_DOWNLOADING=false;
        static OkHttpClient m_OkHttpClient = new OkHttpClient();

        private  static String getResponse(URL url) throws IOException {
        Log.d(LOG_TAG,"getResponse(), Uri built: "+url.toString());
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        String response="";
        try {
            InputStream in = connection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            if(scanner.hasNext())
                response += scanner.next();
            Log.i(LOG_TAG, "JSON response: " + response);
        }
        finally {
            connection.disconnect();
        }

        return response;
    }
        private  static String getResponse_OkHttp(URL url){
            Log.i(LOG_TAG,"getResponse_OkHttp()");
            String responseStr="[]";
            try {
                Request request= new Request.Builder()
                        .url(url)
                        .build();
                Response response = m_OkHttpClient.newCall(request).execute();
                responseStr= response.body().string();
                response.body().close();
            }
            catch (IOException e) {
                Log.e(LOG_TAG, "IO exception: " + e.toString());
                e.printStackTrace();
            }

            return responseStr;
        }
        public static DogEntry downloadDogData( final int dogID) {
            DogEntry downloadedEntry = JsonDogDataParser.invalidDogEntry();

            //Build the download link
            String idStr = String.valueOf(dogID);
            URL downloadUrl = UrlContract.buildDownloadURL(idStr);
            try {
                //Download using Okhttp and parse downloaded json
                String response = getResponse(downloadUrl);

                downloadedEntry = JsonDogDataParser.parseJson(response);


            }catch (IOException e){
                e.printStackTrace();
                Log.e(LOG_TAG,"ERROR DOWNLOADING JSON: "+ e.toString());
            }
            return downloadedEntry;
        }

        public static boolean connectionAvailable(@Nullable Context context){
            Log.i(LOG_TAG,"connectionAvailable()");
            if(context==null) {
                Log.i(LOG_TAG,"Application context is null");
                return false;
            }

            boolean connAvailable = false;
            if(isNetworkAvailable(context)){
                String urlConnectionTest = "http://www.google.com";
                try{
                    HttpURLConnection conn = (HttpURLConnection) (new URL(urlConnectionTest).openConnection());
                    conn.setRequestProperty("User-Agent","Test");
                    conn.setRequestProperty("Connection","close");

                    conn.setConnectTimeout(1500);
                    conn.connect();
                    return (conn.getResponseCode()==200);
                }catch (IOException e){
                    Log.e(LOG_TAG,"Error checking internet connection: "+ e.toString());
                    e.printStackTrace();
                }
            }
            else
                Log.e(LOG_TAG,"Network is unavailable");

            return  connAvailable;
        }

        private static boolean isNetworkAvailable(Context context){
            ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo =  connectivityManager.getActiveNetworkInfo();
            return (netInfo!=null);
        }

}
