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
    	 String url = "https://www.googleapis.com/youtube/v3/search";
         String params = String.format("part=id,snippet&q=%s&maxResults=%d&key=%s", gameName.trim() + "trailer", 1, apiKey);
         String response = restTemplate.getForObject(url + "?" + params, String.class);
        try {
            JsonNode root = objectMapper.readTree(response);
            JsonNode items = root.path("items");
            if (items.isArray() && items.size() > 0) {
                String videoId = items.get(0).path("id").path("videoId").asText();
                return "https://www.youtube.com/embed/" + videoId;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "No trailer found";
    }
}
