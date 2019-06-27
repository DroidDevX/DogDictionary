package com.droiddevsa.doganator.utils;

import android.util.Log;

import com.droiddevsa.doganator.data.DogEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonDogDataParser {
    private static final String LOG_TAG = JsonDogDataParser.class.getSimpleName();

    public static DogEntry invalidDogEntry(){
        DogEntry invalidDogEntry =new DogEntry("","","","","","","","");
        invalidDogEntry.setValid(false);
        return invalidDogEntry;
    }


  public static DogEntry parseJson(String jsonDogData)
    {
        try{

            JSONArray root = new JSONArray(jsonDogData);

            JSONObject firstBreed = (JSONObject) root.get(0);
            String imageURl = firstBreed.getString("url");


            JSONArray breedInfoArray = firstBreed.getJSONArray("breeds");
                JSONObject breedInfoJson = (JSONObject) breedInfoArray.get(0);
                String idStr = breedInfoJson.getString("id");
                int id = Integer.parseInt(idStr);
                String dogName = breedInfoJson.getString("name");
                String bredFor = breedInfoJson.getString("bred_for");
                String lifeSpan = breedInfoJson.getString("life_span");
                JSONObject weight = breedInfoJson.getJSONObject("weight");
                String weightKg = weight.getString("metric");
                JSONObject height = breedInfoJson.getJSONObject("height");
                String heightMetric = height.getString("metric");
                String breedGroup = breedInfoJson.getString("breed_group");
                String temperament = breedInfoJson.getString("temperament");
                DogEntry dogModel =new DogEntry(id,dogName,weightKg+" kg",heightMetric+" cm",breedGroup,bredFor,lifeSpan,temperament,imageURl);
                Log.i(LOG_TAG,"DOG ENTRY: "+dogModel.toString());
                return dogModel;

        }catch (JSONException e)
        {
            Log.e(LOG_TAG,"FAILED TO INITIALIZE JSON OBJECT: "+e.toString());
            e.printStackTrace();
            return invalidDogEntry();
        }

    }
}
