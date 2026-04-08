package com.naveen.aiplayground.controller;

import com.naveen.aiplayground.service.AIModelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/1.0/ai-model-interaction")
public class AIRestController {

    private final Logger LOGGER = LoggerFactory.getLogger(AIRestController.class);

    private final Map<String , AIModelClient> XAIModelClientMap;

    public AIRestController(Map<String, AIModelClient> aiModelClientMap) {
        this.aiModelClientMap = aiModelClientMap;
    }

    @PostMapping
    public ResponseEntity<Optional<String>> askModel(
            @RequestParam(defaultValue = "claude") String withModel,
            @RequestBody String message) {
        LOGGER.info("Connecting model :{}", withModel);
        return ResponseEntity.ok().body(aiModelClientMap.get(withModel).sendMessage(message));
    }
}
