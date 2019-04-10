package com.droiddevsa.doganator.Utils;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class DogApiNetworkUtils {
        //Helper class that makes API calls to a backend server to retrieve dog info and images
        private static final String LOG_TAG = DogApiNetworkUtils.class.getSimpleName();

        //BASE URL
        public static final String SCHEME ="https://";
        public static final String AUTHORITY ="api.thecatapi.com/";
        public static final String VERSION = "v1/";
        public static final String BASE_URL= SCHEME+ AUTHORITY+VERSION;

        //PATHS
        public static final String IMAGE_PATH = "images/search";

        //QUERY PARAMs
        public static final String AUTHORIZATION_PARAM="x-api-key";
        public static final String HASH_KEY ="8679c293-deb8-4c9e-94aa-f01221b3782a";

        //CONTENT URIs
        public static final String IMAGE_CONTENT_URI = BASE_URL+IMAGE_PATH;

        public static  URL buildImagesUri(){
            //Refer to https://docs.thedogapi.com/api-reference/images/images-search
            Uri uri = Uri.parse(IMAGE_CONTENT_URI).buildUpon()
                            .appendQueryParameter(AUTHORIZATION_PARAM,HASH_KEY)
                            .appendQueryParameter("size","small")
                            .appendQueryParameter("mime_types","true")
                            .appendQueryParameter("order","ASC")
                            .appendQueryParameter("limit","8") /*Download 8 images*/
                            .appendQueryParameter("page","0") /*page =starting index=0*/
                           // .appendQueryParameter("category_ids","true")
                            .appendQueryParameter("format","json").build();
             URL url=null;
             try{
                     url =new URL(uri.toString());// **
             }catch (MalformedURLException e)
             {
                     //TODO - remove later
                     Log.d(LOG_TAG,"URL ERROR: "+e.toString());
                     e.printStackTrace();
             }
            return url;
        }

        public static String getResponse(URL url) throws IOException
        {
                Log.d(LOG_TAG,"getResponse(), Uri built: "+url.toString());
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                String response=""; //TODO -remove
                try {
                    InputStream in = connection.getInputStream();
                    //TODO -test string first before using scanner, remove Log after testing
                    Scanner scanner = new Scanner(in);
                        if(scanner.hasNext())
                            response += scanner.next();
                    Log.i(LOG_TAG, "JSON response: " + response);
                    //Scanner scanner = new Scanner(in);

                }finally {
                    connection.disconnect();
                }
                return response;
        }



}
