# AI Playground

A Spring Boot project for learning to build AI agents using the Claude API by Anthropic.

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
2. Set your API key as a runtime environment variable (see below)
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

## Generating Your Anthropic API Key

1. Go to [console.anthropic.com](https://console.anthropic.com)
2. Sign in or create a free account
3. Navigate to **Settings → API Keys**
4. Click **Create Key**
5. Give it a name (e.g. `ai-playground`)
6. Copy the key immediately — it is only shown once

> Note: The Anthropic API requires credits to use. New accounts can add a payment method and receive free starting credits.

---

## Setting Your API Key

This project reads the API key from a runtime environment variable so the actual key is never stored in the codebase.

### In IntelliJ IDEA

1. Go to `Run → Edit Configurations`
2. Select your Spring Boot run configuration
3. Click `Modify options → Environment variables`
4. Add the following:

```
ANTHROPIC_API_KEY=sk-ant-your-actual-key-here
```

5. Click **Apply** and **OK**

### On Mac / Linux (system level)

Add this to your `~/.zshrc` or `~/.bashrc`:

```bash
export ANTHROPIC_API_KEY=sk-ant-your-actual-key-here
```

Then reload:

```bash
source ~/.zshrc
```

### On Windows

1. Open **System Properties → Environment Variables**
2. Under **User variables**, click **New**
3. Variable name: `ANTHROPIC_API_KEY`
4. Variable value: `sk-ant-your-actual-key-here`

---

## How the API Key is Used

In `application.properties`, the key is referenced as an environment variable — no actual value is stored:

```properties
ANTHROPIC_API_KEY=${ANTHROPIC_API_KEY}
```

In the Java code, it is injected via Spring's `@Value` annotation:

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

---

## Testing the API

Once the server is running, open your browser and go to:

```
http://localhost:8080
```

You will see the AI Playground chat UI where you can start sending messages to Claude directly.

You can also test via curl:

```bash
curl -X POST http://localhost:8080/api/claude/ask \
  -H "Content-Type: text/plain" \
  -d "Hello Claude!"
```

---

## Tech Stack

- Java 17
- Spring Boot 4.0.5
- Gradle
- Anthropic Java SDK
- Claude Sonnet 4.6

---

## Author

Naveen — [github.com/iamnaveen93](https://github.com/iamnaveen93)
