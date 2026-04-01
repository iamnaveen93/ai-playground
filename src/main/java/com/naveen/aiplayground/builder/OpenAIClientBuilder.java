package com.naveen.aiplayground.builder;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OpenAIClientBuilder {

    private final OpenAIClient openAiClient;

    public OpenAIClientBuilder(@Value("${OPENAI_API_KEY}") String apiKey) {
        this.openAiClient = OpenAIOkHttpClient.builder().apiKey(apiKey).build();
    }

    public final Optional<String> askOpenAIModel(final String message) {
        ChatCompletionCreateParams messageCreateParams = ChatCompletionCreateParams.builder()
                .model(ChatModel.GPT_4O)
                .maxCompletionTokens(1024L)
                .addUserMessage(message)
                .build();
        ChatCompletion response = openAiClient.chat().completions().create(messageCreateParams);
        return response.choices().getFirst().message().content();
    }
}
