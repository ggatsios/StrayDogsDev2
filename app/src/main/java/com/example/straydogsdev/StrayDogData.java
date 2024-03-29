package com.example.straydogsdev;

import java.util.Date;

public class StrayDogData {
    private String id;
    private String name;
    private String location;
    private String color;
    private String breed;
    private String gender;
    private String description;
    public String photoUrl;
    private double latitude;
    private double longitude;
    private Date dateAdded;


    public StrayDogData(String id, String name, String location, String color, String breed, String selectedGender, String description, String photoUrl, double latitude, double longitude, Date dateAdded) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.color = color;
        this.breed = breed;
        this.gender = selectedGender;
        this.description = description;
        this.photoUrl = photoUrl;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dateAdded = dateAdded;
    }

    public StrayDogData() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getColor() {
        return color;
    }

    public String getBreed() {
        return breed;
    }

    public String getGender() {
        return gender;
    }

    public String getDescription() {
        return description;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }
    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
    public Date getDateAdded() {return dateAdded;}


    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public void setDateAdded(Date dateAdded) {this.dateAdded = dateAdded;}


    @Override
    public String toString() {
        return "StrayDogData{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", color='" + color + '\'' +
                ", breed='" + breed + '\'' +
                ", gender='" + gender + '\'' +
                ", description='" + description + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                '}';
    }
}
