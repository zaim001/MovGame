package com.movigame.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
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
	
	    @GetMapping("/prompt")
	    public List<String> getResponse(String prompt) {
	        return geminiService.callApi("recommand 5 famous games like the witcher 3.give only game title");
	    }


}
