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

    public static DogDatabase getInstance(Context context){
        if(sInstance==null)
            synchronized (LOCK)
            {
                //What if you call on getInstance() on both
                /*MainActivity.java context (first) then
                * say SecondActivity context(second)
                *
                * If getInstance is called the second time where the
                * SecondActivity.java context is passed as an arg
                * to getInstance(), will sInstance be instanntiated
                * because static classes only belong to one activity...
                * or not because static class , once defined static
                * data within the class will persist across multiple
                * activities*/
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        DogDatabase.class,DogContract.DATABASE_NAME).build();
            }
            return  sInstance;
    }

    public abstract DogDao dogDao();


}
