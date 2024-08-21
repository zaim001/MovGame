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
import com.movigame.entity.GameDetails;
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
    private final ObjectMapper objectMapper;

    public RawgService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.objectMapper = new ObjectMapper();
    }

    public List<Game> getGameInfo(List<String> gameTitles) {
        List<Game> gameInfos = new ArrayList<>();
        for (String gameTitle : gameTitles) {
            String url = rawgApiUrl + "?key=" + rawgApiKey + "&search=" + gameTitle;
            String response = restTemplate.getForObject(url, String.class);
            Game game = extractFirstResult(response);
            if (game != null) {
                gameRepo.save(game);
                gameInfos.add(game);
            }
        }
        return gameInfos;
    }
    //I always extract the first search result from Rawg.io
    private Game extractFirstResult(String responseBody) {
        try {
            JsonNode rootNode = objectMapper.readTree(responseBody);
            JsonNode resultsNode = rootNode.path("results");
            
            if (resultsNode.isArray() && resultsNode.size() > 0) {
                JsonNode gameNode = resultsNode.get(0);
                return createGameFromNode(gameNode);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract game info", e);
        }
        return null;
    }

    private Game createGameFromNode(JsonNode gameNode) {
        Game game = new Game();
        game.setId(game.getId());
        game.setRawgId(gameNode.path("id").asLong());
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
        
        List<String> shortScreenshots = new ArrayList<>();
        JsonNode shortScreenshotsNode = gameNode.path("short_screenshots");
        for (JsonNode screenshotNode : shortScreenshotsNode) {
        	shortScreenshots.add(screenshotNode.path("image").asText());
        }
        game.setShortScreenshots(shortScreenshots);
        
        return game;
    }
    public List<Game> getAllGames() {
        return gameRepo.findAll(); // Fetch all games from the database
    }
    
    public Game getGameById(Long id) {
    	Game game = gameRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Game not found with ID: " + id));
    	return game;
    }
    
    public GameDetails getGameDetailsByRawgId(Long rawgId) {
        String url = rawgApiUrl + "/" + rawgId + "?key=" + rawgApiKey;
        String response = restTemplate.getForObject(url, String.class);
        return extractGameDetails(response);
    }
    private GameDetails extractGameDetails(String responseBody) {
        try {
            JsonNode gameNode = objectMapper.readTree(responseBody);
            return createGameDetailsFromNode(gameNode);
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract game details", e);
        }
    }
    private GameDetails createGameDetailsFromNode(JsonNode gameNode) {
    	
        GameDetails gamedetails = new GameDetails();
        
        Long rawgId = gameNode.path("id").asLong();
        gamedetails.setRawgId(rawgId);
        
        Game game = gameRepo.findByRawgId(rawgId)
                .orElseThrow(() -> new IllegalArgumentException("Game not found for rawgId: " + rawgId));
        gamedetails.setGame(game);
        
        gamedetails.setDescription(gameNode.path("description").asText());
        gamedetails.setBackground_image_additional(gameNode.path("background_image_additional").asText());
        
        
        return gamedetails;
    }

}
