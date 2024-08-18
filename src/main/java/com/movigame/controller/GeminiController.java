package com.movigame.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.movigame.entity.Game;
import com.movigame.service.GeminiService;


@RestController
@CrossOrigin(
        origins = "http://localhost:4200",
        allowedHeaders = "*" 
)
public class GeminiController {
	
	@Autowired
	GeminiService geminiService;
	
	 
	    public GeminiController(GeminiService geminiService) {
	        this.geminiService = geminiService;
	    }
	
	    @PostMapping("/recommend")
	    public ResponseEntity<List<Game>> getGameRecommendations(@RequestBody String moviePrompt) {
	        List<Game> recommendedGames = geminiService.getGameRecommendations(moviePrompt);
	        return ResponseEntity.ok(recommendedGames);
	    }


}
