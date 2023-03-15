package com.example.straydogsdev;

public class StrayDogData {
    private String id;
    private String name;

    private String color;
    private String breed; //It may be removed
    private String gender;
    private String imageUrl;
    private String description;
    private String location;

//    public StrayDogData() {
//        // Default constructor required for calls to DataSnapshot.getValue(DogModel.class)
//    }

    public StrayDogData(String id, String name, String breed, String gender, String imageUrl, String description, String location) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.breed = breed;
        this.gender = gender;
        this.imageUrl = imageUrl;
        this.description = description;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor(){
        return color;

    }

    public String getBreed() {
        return breed;
    }

    public String getGender() {
        return gender;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color){
        this.color = color;
    }
    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }



    // Override the toString method to print the object's attributes
    @Override
    public String toString() {
        return "DogModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", color ='" + color + '\'' +
                ", breed='" + breed + '\'' +
                ", gender='" + gender + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
