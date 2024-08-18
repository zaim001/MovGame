package com.movigame.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movigame.entity.Game;
import com.movigame.repo.GameRepo;

@Service
public class RawgService {

	@Autowired
	private GameRepo gameRepo;
	
    @Value("${rawg.api.url}")
    private String rawgApiUrl;

    @Value("${rawg.api.key}")
    private String rawgApiKey;

    private final RestTemplate restTemplate;

    public RawgService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<String> getGameInfo(List<String> gameTitles) {
        List<String> gameInfos = new ArrayList<>();
        for (String gameTitle : gameTitles) {
            String url = rawgApiUrl + "?key=" + rawgApiKey + "&search=" + gameTitle;
            String response = restTemplate.getForObject(url, String.class);
            String gameInfo = extractFirstResult(response);
            // Save game to database
            saveGameToDatabase(gameInfo);

            gameInfos.add(gameInfo);
        }
        return gameInfos;
    }
    
    private void saveGameToDatabase(String gameInfo) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode gameNode = objectMapper.readTree(gameInfo);
            Game game = new Game();
            // game.setSlug(gameNode.path("slug").asText());
            game.setName(gameNode.path("name").asText());
            game.setReleased(gameNode.path("released").asText());
            game.setBackgroundImage(gameNode.path("background_image").asText());
            game.setRating(gameNode.path("rating").asDouble());
            
            List<String> genres = new ArrayList<>();
            JsonNode genresNode = gameNode.path("genres");
            for (JsonNode genreNode : genresNode) {
                genres.add(genreNode.path("name").asText());
            }
            game.setGenres(genres); 
            
            List<String> platforms = new ArrayList<>();
            JsonNode parentPlatformsNode = gameNode.path("parent_platforms");
            if (parentPlatformsNode.isArray()) {
                for (JsonNode platformNode : parentPlatformsNode) {
                    JsonNode platform = platformNode.path("platform");
                    String platformName = platform.path("name").asText();
                    platforms.add(platformName);
                }
            }
            game.setPlateforms(platforms);
            
            // Save to repository
            gameRepo.save(game);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save game to database", e);
        }
    }

    private String extractFirstResult(String responseBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Parse the response body to a JsonNode
            JsonNode rootNode = objectMapper.readTree(responseBody);
            // Navigate to the 'results' node
            JsonNode resultsNode = rootNode.path("results");
            
            // Check if the resultsNode is an array and has at least one element
            if (resultsNode.isArray() && resultsNode.size() > 0) {
                // Get the first element of the array
                JsonNode firstResult = resultsNode.get(0);
                // Return the JSON string representation of the first result
                return objectMapper.writeValueAsString(firstResult);
            } else {
                // If resultsNode is empty or not an array, return a message or handle it accordingly
                return "{}";  // Return an empty JSON object or a specific message if desired
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract game info", e);
        }
    }

}
