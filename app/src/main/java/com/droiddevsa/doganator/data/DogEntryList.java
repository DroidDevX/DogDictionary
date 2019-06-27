package com.droiddevsa.doganator.data;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

public class DogEntryList  implements Parcelable {
    public static Creator<DogEntryList> CREATOR = new Creator<DogEntryList>() {
        @Override
        public DogEntryList createFromParcel(Parcel source) {
            return new DogEntryList(source);
        }

        @Override
        public DogEntryList[] newArray(int size) {
            return new DogEntryList[0];
        }
    };
    ArrayList<DogEntry> dogEntries;

    public List<DogEntry> getListValue(){
        return dogEntries;
    }

    DogEntryList(Parcel source){
        this.dogEntries = source.readArrayList(null);
    }

    public DogEntryList(){
        this.dogEntries= new ArrayList<>();
    }

    public  void addList(List<DogEntry> list){
        this.dogEntries.addAll(list);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.dogEntries);
    }
}