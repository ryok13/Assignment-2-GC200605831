package com.georgiancollege.assignment2gc200605831;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

// Controller class for the dog image view screen / Displays random images of the selected dog breed and allows reloading or going back
public class DogImagesController {

    @FXML
    private Label breedLabel;

    @FXML
    private ImageView dogImg1, dogImg2, dogImg3;

    @FXML
    private Button backBtn;


    // Handles the "Show Another" button click / Reloads new random images for the selected breed
    @FXML
    void onShowAnotherClicked() {
        loadImages();
    }

    // Handles the "Back" button click / Navigates back to the breed selection screen
    @FXML
    void onBackClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("breed-selection-view.fxml"));
            Scene root = new Scene(loader.load());
            Stage stage = (Stage) backBtn.getScene().getWindow();
            stage.setScene(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Breed breed;
    // Sets the selected breed and displays its name and images
    public void setBreed(Breed breed) {
        this.breed = breed;
        breedLabel.setText(breed.toString());
        loadImages();
    }

    // Loads up to 3 random images of the selected breed using the Dog API / Handles edge cases where fewer than 3 images are returned
    public void loadImages() {
        try{
            DogApi api = new DogApi();
            List<String> imageUrls = api.fetchRandomImages(breed.getFullPath(), 3);

            //  Clear previous images
            dogImg1.setImage(null);
            dogImg2.setImage(null);
            dogImg3.setImage(null);

            int count = imageUrls.size();

            if (count == 0) {
                System.out.println("No images found for breed: " + breed.getFullPath());
            } else if (count == 1) {
                dogImg1.setImage(new Image(imageUrls.get(0)));
                System.out.println("Only one picture for this breed");
            } else if (count == 2) {
                dogImg1.setImage(new Image(imageUrls.get(0)));
                dogImg2.setImage(new Image(imageUrls.get(1)));
            } else {
                // Default case: 3 images available
                dogImg1.setImage(new Image(imageUrls.get(0)));
                dogImg2.setImage(new Image(imageUrls.get(1)));
                dogImg3.setImage(new Image(imageUrls.get(2)));
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}

