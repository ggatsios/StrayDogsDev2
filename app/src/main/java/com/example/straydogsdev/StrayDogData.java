package com.example.straydogsdev;

public class StrayDogData {
    private String id;
    private String name;
    private String location;
    private String color;
    private String breed; //It may be removed
    private String gender;
    private String description;

//    public StrayDogData() {
//        // Default constructor required for calls to DataSnapshot.getValue(DogModel.class)
//    }

    public StrayDogData(String id, String name, String location, String color, String breed, String gender,String description) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.color = color;
        this.breed = breed;
        this.gender = gender;
        this.description = description;
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
    public String getColor(){return color;}
    public String getBreed() {
        return breed;
    }

    public String getGender() {
        return gender;
    }

    public String getDescription() {
        return description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setLocation(String location) {
        this.location = location;
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


    public void setDescription(String description) {
        this.description = description;
    }




    // Override the toString method to print the object's attributes
    @Override
    public String toString() {
        return "DogModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", color ='" + color + '\'' +
                ", breed='" + breed + '\'' +
                ", gender='" + gender + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
