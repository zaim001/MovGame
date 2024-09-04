package com.movigame.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.movigame.entity.Game;
import com.movigame.entity.GameDetails;
import com.movigame.service.GeminiService;
import com.movigame.service.RawgService;
import com.movigame.service.YoutubeService;


@RestController
@CrossOrigin(
        origins = "https://movgame-production.up.railway.app/",
        allowedHeaders = "*" 
)
public class GameController {
	
	@Autowired
	private GeminiService geminiService;
	
	@Autowired
	private RawgService rawgService;
		
	 
	    public GameController(GeminiService geminiService) {
	        this.geminiService = geminiService;
	    }
	
	    @PostMapping("/recommend")
	    public ResponseEntity<List<Game>> getGameRecommendations(@RequestBody String moviePrompt) {
	        List<Game> recommendedGames = geminiService.getGameRecommendations(moviePrompt);
	        return ResponseEntity.ok(recommendedGames);
	    }
	    
	    @GetMapping("games")
	    public ResponseEntity<List<Game>> getAllGames() {
	        List<Game> games = rawgService.getAllGames();
	        return ResponseEntity.ok(games); // Return the list of all games
	    }
	    
		@GetMapping("/games/{id}")
	    public ResponseEntity<Game> getGameById(@PathVariable("id") Long id) {
	        Game game = rawgService.getGameById(id);
	        return ResponseEntity.ok(game);
	    }
		@GetMapping("/games/details/{rawgId}")
		public ResponseEntity<GameDetails> getGameDetailsByRawgId(@PathVariable("rawgId") Long rawgId) {
		    GameDetails gamed = rawgService.getGameDetailsByRawgId(rawgId);
		    return ResponseEntity.ok(gamed);
		}

	


}
