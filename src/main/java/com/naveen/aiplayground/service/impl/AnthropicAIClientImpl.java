package com.naveen.aiplayground.service.impl;

import com.naveen.aiplayground.builder.AnthropicAIClientBuilder;
import com.naveen.aiplayground.service.AIClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnthropicAIClientImpl implements AIClient {

    @Autowired
    final AnthropicAIClientBuilder anthropicAIClientBuilder;

    public AnthropicAIClientImpl(AnthropicAIClientBuilder anthropicAIClientBuilder) {
        this.anthropicAIClientBuilder = anthropicAIClientBuilder;
    }

    @Override
    public String askClient(final String message) {
        return anthropicAIClientBuilder.askClaudeModel(message);
    }
}
