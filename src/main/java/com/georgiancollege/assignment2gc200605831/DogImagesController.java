package com.georgiancollege.assignment2gc200605831;

import javafx.event.ActionEvent;
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

public class DogImagesController {

    @FXML
    private Label breedLabel;

    @FXML
    private ImageView dogImg1, dogImg2, dogImg3;

    @FXML
    private Button showAnotherBtn, backBtn;


    @FXML
    void onShowAnotherClicked(ActionEvent event) {
        loadImages();
    }

    @FXML
    void onBackClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("breed-selection-view.fxml")); // ←あなたの前の画面のF XML ファイル名
            Scene root = new Scene(loader.load());
            Stage stage = (Stage) backBtn.getScene().getWindow();
            stage.setScene(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Breed breed;
    public void setBreed(Breed breed) {
        this.breed = breed;
        breedLabel.setText(breed.toString());
        loadImages();
    }

    public void loadImages() {
        try{
            DogApi api = new DogApi();
            List<String> imageUrls = api.fetchRandomImages(breed.getFullPath(), 3);

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
                // 通常：3枚
                dogImg1.setImage(new Image(imageUrls.get(0)));
                dogImg2.setImage(new Image(imageUrls.get(1)));
                dogImg3.setImage(new Image(imageUrls.get(2)));
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}

