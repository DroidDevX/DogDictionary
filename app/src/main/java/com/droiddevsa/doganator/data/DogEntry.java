package com.droiddevsa.doganator.data;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity (tableName = "Dog")
public class DogEntry implements Parcelable {

    @PrimaryKey(autoGenerate = true)

    private  int dogID;
    private String dogBreed;
    private String weight;
    private String height;
    private String breedGroup;
    private String bredFor;
    private String temperament;
    private String lifeSpan;
    private String imageUrl;

    public static Creator<DogEntry> CREATOR = new Creator<DogEntry>() {
        @Override
        public DogEntry createFromParcel(Parcel source) {
            int id = source.readInt();
            String dogBreed = source.readString();
            String weight = source.readString();
            String height = source.readString();
            String breedGroup = source.readString();
            String bredFor = source.readString();
            String lifeSpan = source.readString();
            String temperament = source.readString();
            String imageUrl = source.readString();
            return new DogEntry(id,dogBreed,weight,height,breedGroup,bredFor,lifeSpan,temperament,imageUrl);

        }

        @Override
        public DogEntry[] newArray(int size) {
            return new DogEntry[0];
        }


    };


    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(this.dogID);
        dest.writeString(this.dogBreed);
        dest.writeString(this.weight);
        dest.writeString(this.height);
        dest.writeString(this.breedGroup);
        dest.writeString(this.bredFor);
        dest.writeString(this.lifeSpan);
        dest.writeString(this.temperament);
        dest.writeString(this.imageUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Ignore
    private boolean isValid;

    public void setValid(boolean isValid)
    {
        this.isValid = isValid;
    }

    public boolean isValid()
    {
        return isValid;
    }

    @Override
    public String toString() {
        return "DogModel{" +
                "id="+ dogID + '\'' +
                ", dogbreed='" + dogBreed + '\'' +
                ", bredFor='" + bredFor + '\'' +
                ", weight='" + weight + '\'' +
                ", height='" + height + '\'' +
                ", breedGroup='" + breedGroup + '\'' +
                ", lifeSpan='" + lifeSpan + '\'' +
                ", temperament='" + temperament + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    @Ignore
    public DogEntry(String dogBreed,String weight,String height, String breedGroup,String bredFor, String lifeSpan,String temperament, String imageUrl)
    {

        this.dogBreed = dogBreed;
        this.weight=weight;
        this.height = height;
        this.breedGroup= breedGroup;
        this.bredFor = bredFor;
        this.lifeSpan = lifeSpan;
        this.temperament = temperament;
        this.imageUrl = imageUrl;
        this.isValid=true;
    }

    public DogEntry(int dogID,String dogBreed,String weight,String height, String breedGroup,String bredFor, String lifeSpan,String temperament, String imageUrl)
    {
        this.dogID =dogID;
        this.dogBreed = dogBreed;
        this.weight=weight;
        this.height = height;
        this.breedGroup=breedGroup;
        this.bredFor = bredFor;
        this.lifeSpan = lifeSpan;
        this.temperament = temperament;
        this.imageUrl = imageUrl;
        this.isValid=true;
     }

    public void setDogID(int id) {
        this.dogID = id;
    }

    public void setBredFor(String bredFor) {
        this.bredFor = bredFor;
    }

    public void setBreedGroup(String breedGroup) {
        this.breedGroup = breedGroup;
    }

    public void setDogBreed(String dogBreed) {
        this.dogBreed = dogBreed;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setLifeSpan(String lifeSpan) {
        this.lifeSpan = lifeSpan;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setTemperament(String temperament) {
        this.temperament = temperament;
    }

    public int getDogID() {
        return dogID;
    }
    public String getDogBreed(){return dogBreed;}
    public String getWeight(){return weight;}
    public String getHeight(){return height;}
    public String getBreedGroup(){return breedGroup;}
    public String getBredFor(){return bredFor;}
    public String getLifeSpan(){return lifeSpan;}
    public String getTemperament(){return temperament;}
    public String getImageUrl(){return imageUrl;}


}
