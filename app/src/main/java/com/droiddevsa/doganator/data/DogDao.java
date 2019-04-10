package com.droiddevsa.doganator.data;


import java.util.List;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface DogDao {

    @Query("SELECT * FROM Dog")
    LiveData<List<DogEntry>> queryDogData();

    @Insert
    void insertDogData(DogEntry entry);

}
