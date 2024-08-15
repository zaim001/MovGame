package com.movigame.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RawgService {
	
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
            gameInfos.add(extractFirstResult(response));
        }
        return gameInfos;
    }

    private String extractFirstResult(String responseBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(responseBody);
            JsonNode resultsNode = rootNode.path("results");
            if (resultsNode.isArray() && resultsNode.size() > 0) {
                JsonNode firstResult = resultsNode.get(0);
                return firstResult.toString();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract game info", e);
        }
        return null;
    }

}
