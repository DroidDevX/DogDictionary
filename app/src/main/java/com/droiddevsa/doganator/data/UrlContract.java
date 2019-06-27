package com.droiddevsa.doganator.data;

import android.net.Uri;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;


public class UrlContract {

    final static  String LOG_TAG = UrlContract.class.getSimpleName();
    //BASE URL
    private static final String SCHEME ="https://";
    private static final String AUTHORITY ="api.thedogapi.com/";
    private static final String VERSION = "v1/";
    private static final String BASE_URL= SCHEME+ AUTHORITY+VERSION;
    //PATHS
    private static final String IMAGE_PATH = "images/search";
    //QUERY PARAMs
    private static final String AUTHORIZATION_PARAM="x-api-key";
    private static final String HASH_KEY ="8679c293-deb8-4c9e-94aa-f01221b3782a";
    //CONTENT URIs
    private static final String IMAGE_CONTENT_URI = BASE_URL+IMAGE_PATH;


    public  static URL buildDownloadURL(String breed_ID){
        //Refer to https://docs.thedogapi.com/api-reference/images/images-search
        Log.i(LOG_TAG,"Breed id: "+ breed_ID);
        Uri uri = Uri.parse(IMAGE_CONTENT_URI).buildUpon()
                .appendQueryParameter(AUTHORIZATION_PARAM,HASH_KEY)
                .appendQueryParameter("size","small")
                .appendQueryParameter("limit","1")
                .appendQueryParameter("page","0")
                .appendQueryParameter("format","json")
                .appendQueryParameter("breed_id",breed_ID)
                .build();
        URL url=null;
        try{
            url =new URL(uri.toString());// **
        }catch (MalformedURLException e)
        {
            Log.d(LOG_TAG,"URL ERROR: "+e.toString());
            e.printStackTrace();
        }
        return url;
    }
}
