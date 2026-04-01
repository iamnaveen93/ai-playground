package com.naveen.aiplayground.builder;

import com.anthropic.client.AnthropicClient;
import com.anthropic.client.okhttp.AnthropicOkHttpClient;
import com.anthropic.models.messages.Message;
import com.anthropic.models.messages.MessageCreateParams;
import com.anthropic.models.messages.Model;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;

@Component
public class AnthropicAIClientBuilder {

    private final AnthropicClient anthropicClient;

    public AnthropicAIClientBuilder(@Value("${ANTHROPIC_API_KEY}") String apiKey) {
        this.anthropicClient = AnthropicOkHttpClient.builder().apiKey(apiKey).build();
    }

    public final String askClaudeModel(final String message) {
        MessageCreateParams messageCreateParams = MessageCreateParams.builder()
                .model(Model.CLAUDE_SONNET_4_6)
                .maxTokens(1024L)
                .addUserMessage(message)
                .build();
        Message message1 = anthropicClient.messages().create(messageCreateParams);
        return message1.content().getFirst().asText().text();
    }
}
