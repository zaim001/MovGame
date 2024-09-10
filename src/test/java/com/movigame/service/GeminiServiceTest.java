package com.movigame.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest

class GeminiServiceTest {
	   
	
	    public GeminiServiceTest(GeminiService geminiService) {
		super();
		this.geminiService = geminiService;
	}


		private final GeminiService geminiService;
	    

	    @Test
	    public void contextLoads() {
	        assertNotNull(geminiService);
	    }

}
