# AI Playground

A Spring Boot project for learning to build AI agents using Claude (Anthropic) and ChatGPT (OpenAI).

---

## Prerequisites

Make sure you have the following installed before getting started:

- Java 17 or above
- Gradle
- Git
- IntelliJ IDEA (or any IDE of your choice)

---

## Getting Started

### 1. Clone the project

Open your terminal and run:

```bash
git clone https://github.com/iamnaveen93/ai-playground.git
```

Then navigate into the project folder:

```bash
cd ai-playground
```

### 2. Open in IntelliJ IDEA

- Open IntelliJ IDEA
- Go to `File → Open`
- Select the `ai-playground` folder
- IntelliJ will automatically detect it as a Gradle project and download all dependencies

---

## Running the Project

### Option 1 — Using IntelliJ IDEA

1. Open the project in IntelliJ
2. Set your API keys as runtime environment variables (see below)
3. Click the **Run** button or press `Shift + F10`
4. The server starts at `http://localhost:8080`

### Option 2 — Using Terminal

```bash
./gradlew bootRun
```

To build the project without running:

```bash
./gradlew clean build
```

---

## Generating Your Anthropic API Key (Claude)

1. Go to [console.anthropic.com](https://console.anthropic.com)
2. Sign in or create a free account
3. Navigate to **Settings → API Keys**
4. Click **Create Key**
5. Give it a name (e.g. `ai-playground`)
6. Copy the key immediately — it is only shown once

> Note: The Anthropic API requires credits to use. New accounts can add a payment method and receive free starting credits.

---

## Generating Your OpenAI API Key (ChatGPT)

1. Go to [platform.openai.com](https://platform.openai.com)
2. Sign in or create a free account
3. Navigate to **Settings → API Keys**
4. Click **Create new secret key**
5. Give it a name (e.g. `ai-playground`)
6. Copy the key immediately — it is only shown once

> Note: The OpenAI API requires credits to use. New accounts can add a payment method at [platform.openai.com/settings/billing](https://platform.openai.com/settings/billing). New accounts start on Tier 0 which has rate limits — adding credits upgrades your tier automatically.

---

## Setting Your API Keys

This project reads API keys from runtime environment variables so actual values are never stored in the codebase.

### In IntelliJ IDEA

1. Go to `Run → Edit Configurations`
2. Select your Spring Boot run configuration
3. Click `Modify options → Environment variables`
4. Add the following:

```
ANTHROPIC_API_KEY=sk-ant-your-actual-key-here
OPENAI_API_KEY=sk-proj-your-actual-key-here
```

5. Click **Apply** and **OK**

### On Mac / Linux (system level)

Add this to your `~/.zshrc` or `~/.bashrc`:

```bash
export ANTHROPIC_API_KEY=sk-ant-your-actual-key-here
export OPENAI_API_KEY=sk-proj-your-actual-key-here
```

Then reload:

```bash
source ~/.zshrc
```

### On Windows

1. Open **System Properties → Environment Variables**
2. Under **User variables**, click **New**
3. Add both variables:

| Variable name | Variable value |
|---|---|
| `ANTHROPIC_API_KEY` | `sk-ant-your-actual-key-here` |
| `OPENAI_API_KEY` | `sk-proj-your-actual-key-here` |

---

## How the API Keys are Used

In `application.properties`, keys are referenced as environment variables — no actual values are stored:

```properties
ANTHROPIC_API_KEY=${ANTHROPIC_API_KEY}
OPENAI_API_KEY=${OPENAI_API_KEY}
openai.api-key=${OPENAI_API_KEY}
openai.base-url=https://api.openai.com/v1
```

Anthropic key is injected via Spring's `@Value` annotation:

```java
@Component
public class AnthropicAIClientBuilder {

    private final AnthropicClient anthropicClient;

    public AnthropicAIClientBuilder(
            @Value("${ANTHROPIC_API_KEY}") String apiKey) {
        this.anthropicClient = AnthropicOkHttpClient.builder()
                .apiKey(apiKey)
                .build();
    }

    public String askClaudeModel(String message) {
        MessageCreateParams params = MessageCreateParams.builder()
                .model(Model.CLAUDE_SONNET_4_6)
                .maxTokens(1024L)
                .addUserMessage(message)
                .build();

        Message response = anthropicClient.messages().create(params);
        return response.content().getFirst().asText().text();
    }
}
```

OpenAI key is auto-configured by the Spring Boot starter — no manual wiring needed:

```java
@Component
public class OpenAIClientBuilder {

    private final OpenAIClient openAIClient;

    public OpenAIClientBuilder(OpenAIClient openAIClient) {
        this.openAIClient = openAIClient;
    }

    public String askOpenAIModel(String message) {
        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .model(ChatModel.GPT_4_O_MINI)
                .addUserMessage(message)
                .maxCompletionTokens(1024)
                .build();

        ChatCompletion completion = openAIClient.chat()
                .completions()
                .create(params);

        return completion.choices()
                .get(0)
                .message()
                .content()
                .orElse("No response");
    }
}
```

---

## Switching Between Models

The project supports switching between Claude and ChatGPT via the UI or API.

### Via the UI

Select the model from the dropdown in the top right of the chat interface.

### Via API

Pass the `withModel` request parameter:

```bash
# Use Claude
curl -X POST "http://localhost:8080/api/v1/1.0/ai-model-interaction?withModel=claude" \
  -H "Content-Type: text/plain" \
  -d "Hello!"

# Use ChatGPT
curl -X POST "http://localhost:8080/api/v1/1.0/ai-model-interaction?withModel=openai" \
  -H "Content-Type: text/plain" \
  -d "Hello!"
```

> If no model is specified, Claude is used by default.

---

## Testing the API

Once the server is running, open your browser and go to:

```
http://localhost:8080
```

You will see the AI Playground chat UI where you can select a model and start chatting.

---

## Tech Stack

- Java 17
- Spring Boot 4.0.5
- Gradle
- Anthropic Java SDK — Claude Sonnet 4.6
- OpenAI Java SDK — GPT-4o Mini
- Vanilla HTML, CSS, JavaScript

---

## Author

Naveen — [github.com/iamnaveen93](https://github.com/iamnaveen93)