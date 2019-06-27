package com.droiddevsa.doganator.data;


import java.util.List;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface DogDao {

    @Query("SELECT * FROM Dog")
    LiveData<List<DogEntry>> queryDogData();


    @Insert
    void insertEntryIntoDatabase_ROOM(DogEntry entry);




}
