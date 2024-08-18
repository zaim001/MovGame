package com.movigame.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Service
public class GeminiService {


    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private RawgService rawgService;
    

    @Value("${gemini.api.key}")
    private String geminiKey;

    @Value("${gemini.api.url}")
    private String apiUrlTemplate;

    public List<String> callApi(String prompt) {
    	
        String apiUrl = apiUrlTemplate + "key=" + geminiKey;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode contentNode = objectMapper.createObjectNode();
        ObjectNode partsNode = objectMapper.createObjectNode();
        partsNode.put("text", prompt);
        contentNode.set("parts", objectMapper.createArrayNode().add(partsNode));
        ObjectNode requestBodyNode = objectMapper.createObjectNode();
        requestBodyNode.set("contents", objectMapper.createArrayNode().add(contentNode));

        String requestBody;
        try {
            requestBody = objectMapper.writeValueAsString(requestBodyNode);
        } catch (Exception e) {
            throw new RuntimeException("Failed to construct JSON request body", e);
        }

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, request, String.class);

        // Extract movie titles from the response
        // return response.getBody();
        List<String> gameTitles = extractGameTitles(response.getBody());
        return rawgService.getGameInfo(gameTitles);
    }

    private List<String> extractGameTitles(String responseBody) {
        List<String> gameTitles = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(responseBody);
            JsonNode candidatesNode = rootNode.path("candidates");
            if (candidatesNode.isArray() && candidatesNode.size() > 0) {
                JsonNode contentNode = candidatesNode.get(0).path("content").path("parts").get(0).path("text");
                String text = contentNode.asText();

                // Extract titles using a simple regex pattern
                String[] lines = text.split("\n");
                for (String line : lines) {
                    if (line.length() > 2) { // Ensure the line is long enough
                        String title = line.substring(2).trim(); // Remove leading "- "
                        title = title.replace("**", "").trim(); // Remove the '**' around the title
                        System.out.println(title);
                        gameTitles.add(title);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract movie titles", e);
        }
        return gameTitles;
    }
}