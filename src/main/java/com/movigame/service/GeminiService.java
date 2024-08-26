package com.movigame.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.movigame.entity.Game;
import com.movigame.repo.GameRepo;

@Service
public class GeminiService {
    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private RawgService rawgService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private GameRepo gameRepo;
    
    @Value("${gemini.api.key}")
    private String geminiKey;

    @Value("${gemini.api.url}")
    private String apiUrlTemplate;
    
   
    public List<Game> getGameRecommendations(String moviePrompt) {
    	gameRepo.deleteAll();
        String apiUrl = apiUrlTemplate + "key=" + geminiKey;
        
        String prompt = "I liked " + moviePrompt + " movie. Can you recommend some games to play that have the same vibe? Give only titles.";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        ObjectNode requestBodyNode = createRequestBody(prompt);

        String requestBody;
        try {
            requestBody = objectMapper.writeValueAsString(requestBodyNode);
        } catch (Exception e) {
            throw new RuntimeException("Failed to construct JSON request body", e);
        }

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, request, String.class);

        List<String> gameTitles = extractGameTitles(response.getBody());
        return rawgService.getGameInfo(gameTitles);
    }

    private ObjectNode createRequestBody(String prompt) {
        ObjectNode contentNode = objectMapper.createObjectNode();
        ObjectNode partsNode = objectMapper.createObjectNode();
        partsNode.put("text", prompt);
        contentNode.set("parts", objectMapper.createArrayNode().add(partsNode));
        ObjectNode requestBodyNode = objectMapper.createObjectNode();
        requestBodyNode.set("contents", objectMapper.createArrayNode().add(contentNode));
        return requestBodyNode;
    }

    private List<String> extractGameTitles(String responseBody) {
        List<String> gameTitles = new ArrayList<>();
        try {
            JsonNode rootNode = objectMapper.readTree(responseBody);
            JsonNode candidatesNode = rootNode.path("candidates");
            if (candidatesNode.isArray() && candidatesNode.size() > 0) {
                JsonNode contentNode = candidatesNode.get(0).path("content").path("parts").get(0).path("text");
                String text = contentNode.asText();
                String[] lines = text.split("\n");
                for (String line : lines) {
                    if (line.length() > 2) {
                        String title = line.substring(2).trim().replace("**", "").trim();
                        gameTitles.add(title);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract game titles", e);
        }
        System.out.println(gameTitles);
        return gameTitles;
    }
}