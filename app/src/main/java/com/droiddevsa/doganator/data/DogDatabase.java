package com.droiddevsa.doganator.data;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {DogEntry.class},version=1,exportSchema = false)
public abstract class DogDatabase extends RoomDatabase {
    private static final String LOG_TAG=DogDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static DogDatabase sInstance;
    private final static String DATABASE_NAME="dogdb";

    public static DogDatabase getInstance(Context context){
        if(sInstance==null)
            synchronized (LOCK)
            {

                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        DogDatabase.class,DATABASE_NAME).build();
            }
            return  sInstance;
    }

    public abstract DogDao dogDao();


}
