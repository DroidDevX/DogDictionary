package com.droiddevsa.doganator.data;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class DogViewModel extends AndroidViewModel {

    private LiveData<List<DogEntry>> dogEntries;

    public LiveData<List<DogEntry>> liveData(){ return dogEntries;}

    public DogViewModel(Application application){
        super(application);
        DogDatabase database = DogDatabase.getInstance(this.getApplication());
        dogEntries = database.dogDao().queryDogData();
    }

}
