package com.georgiancollege.assignment2gc200605831;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BreedSelectionController {

    @FXML
    private TableView<Breed> breedTable;

    @FXML
    private TableColumn<Breed, String> breedColumn;

    @FXML
    private TableColumn<Breed, String> subBreedColumn;

    @FXML
    private TextField searchField;

    @FXML
    private Button showDogBtn;

    private DogApi dogApi;
    private ObservableList<Breed> breedList =  FXCollections.observableArrayList();;
    private List<Breed> allBreeds = new ArrayList();

    @FXML
    public void initialize() {
        dogApi = new DogApi();

        breedColumn.setCellValueFactory(new PropertyValueFactory<>("breed"));
        subBreedColumn.setCellValueFactory(new PropertyValueFactory<>("subBreed"));

        breedTable.setItems(breedList);

        searchField.textProperty().addListener((obs, oldText, newText) -> {
            filterBreeds(newText);
        });

        try{
            allBreeds = dogApi.fetchAllBreeds();
            breedList.setAll(allBreeds);
        } catch (IOException | InterruptedException e) {
            showAlert("Error", "Failed to fetch breeds.", Alert.AlertType.ERROR);
        }
    }

    private void filterBreeds(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            breedList.setAll(allBreeds);
            return;
        }

        String lower = keyword.toLowerCase();
        List<Breed> filtered = new ArrayList<>();

        for (Breed b : allBreeds) {
            if ((b.getBreed() != null && b.getBreed().toLowerCase().contains(lower)) ||
                    (b.getSubBreed() != null && b.getSubBreed().toLowerCase().contains(lower))) {
                filtered.add(b);
            }
        }

        breedList.setAll(filtered);
    }

    @FXML
    private void onShowDogBtnClick(ActionEvent event) {
        String input = searchField.getText().trim().toLowerCase();
        Breed selected = breedTable.getSelectionModel().getSelectedItem();

        if (selected != null) {
            showBreedDetail(selected);
            return;
        }

        if (input.isEmpty()) {
            showAlert("Input Error", "Please enter a breed or subBreed name, or select from the list.", Alert.AlertType.WARNING);
            return;
        }

        // breed or "sub breed breed"
        String[] parts = input.split("\\s+");
        String breed = "", subBreed = "";

        if (parts.length == 1) {
            breed = parts[0];
        } else if (parts.length == 2) {
            subBreed = parts[0];
            breed = parts[1];
        } else {
            showAlert("Input Error", "Enter format: 'breed' or 'subbreed breed'", Alert.AlertType.WARNING);
            return;
        }

        for (Breed b : allBreeds) {
            if (b.getBreed().equalsIgnoreCase(breed) &&
                    (b.getSubBreed() == null && subBreed.isEmpty() ||
                            b.getSubBreed() != null && b.getSubBreed().equalsIgnoreCase(subBreed))) {
                showBreedDetail(b);
                return;
            }
        }

        showAlert("Not Found", "Breed not found. Check spelling.", Alert.AlertType.INFORMATION);
    }

    private void showBreedDetail(Breed breed) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("dog-images-view.fxml"));
            Scene scene = new Scene(loader.load());

            BreedDetailController controller = loader.getController();
            controller.setBreed(breed);

            Stage stage = (Stage) breedTable.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

