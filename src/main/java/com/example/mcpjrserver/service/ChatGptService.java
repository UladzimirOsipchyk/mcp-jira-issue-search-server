package com.example.mcpjrserver.service;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatGptService {

  private final OpenAiService service;

  public ChatGptService() {
    String apiKey = "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";
    this.service = new OpenAiService(apiKey);
  }

  public String generateBugSummary(String russianText) {
    String prompt = """
      You are an assistant that converts user text into a Jira JQL search string.
      
      Tasks:
      1. Translate the input text from Russian to English.
      2. Extract only the key technical terms from the text and translate them to ENGLISH.
      3. Generate a **valid Jira JQL string** to search across all projects:
         - Only use the operator: OR
         - Each keyword must be enclosed in quotes
         - DO NOT include full sentences, explanations, summary, or extra text
         - DO NOT use special characters: *, ?, +, -, ~, !, (, ), [, ], {, }, |
         - Format: summary ~ "word1" OR summary ~ "word2" OR summary ~ "word3"
      
      Output must be strictly the JQL string only, nothing else.
      
      Text: "%s"
      """.formatted(russianText);

    ChatCompletionRequest request = ChatCompletionRequest.builder()
        .model("gpt-4o-mini")
        .messages(List.of(new ChatMessage("user", prompt)))
        .maxTokens(150)
        .temperature(0.7)
        .build();

    String answer = service.createChatCompletion(request)
        .getChoices()
        .get(0)
        .getMessage()
        .getContent()
        .trim();

    return answer;
  }
}
