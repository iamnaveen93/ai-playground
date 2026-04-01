package com.naveen.aiplayground.service.client;

import com.naveen.aiplayground.builder.AnthropicAIClientBuilder;
import com.naveen.aiplayground.service.AIModelClient;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("claude")
public class ClaudeClient implements AIModelClient {

    private final AnthropicAIClientBuilder anthropicAIClientBuilder;

    public ClaudeClient(AnthropicAIClientBuilder anthropicAIClientBuilder) {
        this.anthropicAIClientBuilder = anthropicAIClientBuilder;
    }

    @Override
    public Optional<String> askClient(String message) {
        return anthropicAIClientBuilder.askClaudeModel(message).describeConstable();
    }
}
