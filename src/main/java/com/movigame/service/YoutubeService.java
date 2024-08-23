package com.movigame.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class YoutubeService {
	
	@Autowired
    private RestTemplate restTemplate;
	
	@Autowired
	private ObjectMapper objectMapper;

    @Value("${youtube.api.key}")
    private String apiKey;

    public String searchGameTrailer(String gameName) {
        String url = UriComponentsBuilder.fromHttpUrl("https://youtube.googleapis.com/youtube/v3/search")
                .queryParam("part", "snippet")
                .queryParam("maxResults", 1)
                .queryParam("q", gameName + " game trailer")
                //.queryParam("type", "video")
                //.queryParam("videoEmbeddable", "true")
                .queryParam("key", apiKey)
                .toUriString();

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                JsonNode root = objectMapper.readTree(response.getBody());
                JsonNode items = root.path("items");
                if (items.isArray() && items.size() > 0) {
                    JsonNode firstItem = items.get(0);
                    String videoId = firstItem.path("id").path("videoId").asText();
                    if (!videoId.isEmpty()) {
                        return "https://www.youtube.com/watch?v=" + videoId;
                    }
                    else {
                    	System.out.println("nnnn");
                    }
                }
            } catch (Exception e) {
                // Log the error
                e.printStackTrace();
            }
        }

        return null;
    }
}
