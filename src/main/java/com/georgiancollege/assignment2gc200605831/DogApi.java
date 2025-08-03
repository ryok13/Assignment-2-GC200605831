package com.georgiancollege.assignment2gc200605831;

import com.google.gson.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class DogApi {
    private static final String BASE_URL = "https://dog.ceo/api";
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public List<Breed> fetchAllBreeds() throws IOException, InterruptedException {
        String url = BASE_URL + "/breeds/list/all";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Failed to fetch breeds: " + response.statusCode());
        }

        JsonObject root = JsonParser.parseString(response.body()).getAsJsonObject();
        JsonObject message = root.getAsJsonObject("message");

        List<Breed> breeds = new ArrayList<>();

        for(String breed: message.keySet()) {
            JsonArray subBreeds = message.getAsJsonArray(breed);
            if(subBreeds.size() == 0) {
                breeds.add(new Breed(breed, null));
            } else {
                for(JsonElement sub: subBreeds) {
                    breeds.add(new Breed(breed, sub.getAsString()));
                }
            }
        }

        return breeds;
    }

    public String fetchRandomImage(String breedPath) throws IOException, InterruptedException {
        String url = BASE_URL + "/breeds/" + breedPath + "/images/random";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if(response.statusCode() != 200) {
            throw new IOException("Failed to fetch images: " + response.statusCode());
        }

        JsonObject root = JsonParser.parseString(response.body()).getAsJsonObject();
        return root.get("message").getAsString();
    }

    public List<String> fetchRandomImages(String breedPath, int count) throws IOException, InterruptedException {
        String url = BASE_URL + "/breed/" + breedPath + "/images/random/" + count;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if(response.statusCode() != 200) {
            throw new IOException("Failed to fetch images: " + response.statusCode());
        }

        JsonObject root = JsonParser.parseString(response.body()).getAsJsonObject();
        JsonArray imageArray = root.getAsJsonArray("message");

        List<String> imageUrls = new ArrayList<>();
        for(JsonElement element: imageArray) {
            imageUrls.add(element.getAsString());
        }

        return imageUrls;
    }
}
