package com.nydia.ai.client.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author lvhq
 * @date 2024.06.12
 */
@Service
public class McpServerService implements AiService {

    private final ChatClient chatClient;

    public McpServerService(ChatClient.Builder aiClientBuilder) {
        this.chatClient = aiClientBuilder.build();
    }

    @Override
    public ResponseEntity<String> getAiResponse(String prompt) {
        String response = this.chatClient
                .prompt(prompt)
                .call()
                .content();
        return ResponseEntity.ok(response);
    }

}
