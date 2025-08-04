package com.georgiancollege.assignment2gc200605831;

// Represents a dog breed, which may include a sub-breed
public class Breed {
    private String breed;
    private String subBreed;

    // Constructor to initialize the breed and sub-breed
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

    // Returns the API path format (e.g., "breed/sub-breed" or just "breed")
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

    // Capitalizes the first letter of the string
    private String capitalize(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
