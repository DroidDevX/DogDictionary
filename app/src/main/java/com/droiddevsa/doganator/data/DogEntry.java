package com.droiddevsa.doganator.data;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity (tableName = "Dog")
public class DogEntry {

    @PrimaryKey(autoGenerate = true)
    private  int id;
    private String dogName;
    private String breed;
    private String description;

    @Ignore
    public DogEntry(String dogName, String breed, String description) {
        this.dogName = dogName;
        this.breed = breed;
        this.description = description;
    }

    public DogEntry(int id, String dogName, String breed, String description) {
        this.id = id;
        this.dogName = dogName;
        this.breed = breed;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDogName() {
        return dogName;
    }

    public void setDogName(String dogName) {
        this.dogName = dogName;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "DogEntry{" +
                "id=" + id +
                ", dogName='" + dogName + '\'' +
                ", breed='" + breed + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
