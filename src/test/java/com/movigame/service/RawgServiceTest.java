package com.movigame.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movigame.entity.Game;
import com.movigame.repo.GameRepo;

@ExtendWith(MockitoExtension.class)
public class RawgServiceTest {

    @Mock
    private GameRepo gameRepo;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private RawgService rawgService;

    @BeforeEach
    void setUp() {
        // Setup code here
    }

    @Test
    void testGetGameInfo() throws Exception {
        // Mock JSON response from the RAWG API
        String mockResponse = "{ \"results\": [ { \"id\": 1, \"name\": \"Game Title\", \"released\": \"2023-01-01\", \"background_image\": \"url\", \"rating\": 4.5, \"genres\": [{\"name\": \"Action\"}], \"parent_platforms\": [{\"platform\": {\"name\": \"PC\"}}], \"short_screenshots\": [{\"image\": \"screenshot-url\"}] } ] }";
        
        // Mock behavior of RestTemplate and ObjectMapper
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(mockResponse);
        JsonNode mockNode = new ObjectMapper().readTree(mockResponse);
        when(objectMapper.readTree(mockResponse)).thenReturn(mockNode);
        
        // Call the method under test
        List<Game> result = rawgService.getGameInfo(Arrays.asList("Game Title"));
        
        // Verify interactions and assertions
        verify(gameRepo, times(1)).save(any(Game.class));
        assertEquals(1, result.size());
        assertEquals("Game Title", result.get(0).getName());
    }
    
    @Test
    void testGetGameById() {
        // Implement your test logic for getGameById
    }

    @Test
    void testGetGameDetailsByRawgId() throws Exception {
        // Implement your test logic for getGameDetailsByRawgId
    }
}
