package com.naveen.aiplayground.controller;

import com.naveen.aiplayground.service.AIClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/1.0/ai-model-interaction")
public class AIRestController {

    private final AIClient anthropicClient;

    public AIRestController(@Qualifier("anthropicAIClientImpl") AIClient anthropicClient) {
        this.anthropicClient = anthropicClient;
    }


    @PostMapping(path = "/ask-claude")
    public ResponseEntity<String> askClaude(@RequestBody String message) {
        return ResponseEntity.ok().body(anthropicClient.askClient(message));
    }
}
