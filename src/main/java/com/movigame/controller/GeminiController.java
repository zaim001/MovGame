package com.movigame.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

import com.movigame.service.GeminiService;


@RestController
public class GeminiController {
	
	@Autowired
	GeminiService geminiService;
	
	 
	    public GeminiController(GeminiService geminiService) {
	        this.geminiService = geminiService;
	    }
	
    @GetMapping("/prompt")
    public List<String> getResponse(String prompt) {
        return geminiService.callApi("can you recommand some famous games to play that has same vibe as game of thrones tv show. give only titles nothing more");
    }
  

}
