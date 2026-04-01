package com.naveen.aiplayground.service.client;

import com.naveen.aiplayground.builder.OpenAIClientBuilder;
import com.naveen.aiplayground.service.AIModelClient;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("openAI")
public class OpenAiClient implements AIModelClient {

    private final OpenAIClientBuilder openAIClientBuilder;

    public OpenAiClient(OpenAIClientBuilder openAIClientBuilder) {
        this.openAIClientBuilder = openAIClientBuilder;
    }

    @Override
    public Optional<String> askClient(String message) {
        return openAIClientBuilder.askOpenAIModel(message);
    }
}
