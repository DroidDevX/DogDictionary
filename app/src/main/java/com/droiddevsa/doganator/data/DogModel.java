package com.droiddevsa.doganator.data;

import androidx.room.Ignore;

public class DogModel{

    boolean invalid;
    public String dogbreed;
    public String bredFor;
    public String lifeSpan;
    public String imageUrl;

    @Override
    public String toString() {
        return "DogModel{" +
                "invalid=" + invalid +
                ", dogbreed='" + dogbreed + '\'' +
                ", bredFor='" + bredFor + '\'' +
                ", lifeSpan='" + lifeSpan + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    public DogModel(String dogName, String origin, String bredFor, String lifeSpan, String imageUrl)
    {
        this.dogbreed = dogName;
         this.bredFor = bredFor;
        this.lifeSpan = lifeSpan;
        this.imageUrl = imageUrl;
        this.invalid=false;

    }

    public void setInvalid()
    {
        this.invalid=true;
    }

    public String getDogbreed() {
        return dogbreed;
    }

    public String getBredFor() {
        return bredFor;
    }

    public String getLifeSpan() {
        return lifeSpan;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
