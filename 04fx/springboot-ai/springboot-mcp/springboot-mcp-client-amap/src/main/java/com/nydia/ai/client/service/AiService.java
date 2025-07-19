package com.nydia.ai.client.service;

import org.springframework.http.ResponseEntity;

public interface AiService {
    ResponseEntity<String> getAiResponse(String prompt);

}
