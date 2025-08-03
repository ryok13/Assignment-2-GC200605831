package com.georgiancollege.assignment2gc200605831;

public class Breed {
    private String breed;
    private String subBreed;

    public Breed(String breed, String subBreed) {
        this.breed = breed;
        this.subBreed = subBreed;
    }

    public String getBreed() {
        return breed;
    }

    public String getSubBreed() {
        return subBreed;
    }

    public String getFullPath() {
        if(subBreed == null || subBreed.isEmpty()) {
            return breed;
        } else {
            return breed + "/" + subBreed;
        }
    }

    @Override
    public String toString() {
        if(subBreed == null || subBreed.isEmpty()) {
            return capitalize(breed);
        } else {
            return capitalize(subBreed) + " " + capitalize(breed);
        }
    }

    private String capitalize(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
