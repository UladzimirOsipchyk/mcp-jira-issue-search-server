package com.example.mcpjrserver.controller;

import com.example.mcpjrserver.service.ChatGptService;
import com.example.mcpjrserver.service.JiraSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/execute")
@CrossOrigin(origins = "http://localhost:8080")
public class Controller {

  @Autowired
  private final JiraSearchService jiraSearchService;
  @Autowired
  private final ChatGptService chatGptService;

  public Controller(JiraSearchService jiraSearchService, ChatGptService chatGptService) {
    this.jiraSearchService = jiraSearchService;
    this.chatGptService = chatGptService;
  }

  @GetMapping("/.well-known/mcp")
  public Map<String, Object> manifest() {
    return Map.of(
        "name", "JiraMCP",
        "description", "MCP connector for local Jira Server",
        "tools", List.of(
            Map.of(
                "name", "search_ticket_by_summary",
                "description", "Search Jira tickets by text in summary",
                "readOnlyHint", true,
                "parameters", Map.of(
                    "type", "object",
                    "properties", Map.of(
                        "query", Map.of("type", "string"),
                        "maxResults", Map.of("type", "integer")
                    ),
                    "required", List.of("query")
                )
            )
        )
    );
  }

  @PostMapping("/search_ticket_by_summary")
  public String searchTicket(@RequestBody Map<String, Object> params) {
    String query = (String) params.get("query");
    System.out.println("Incoming Query >>> " + query);
    String jqlQuery = chatGptService.generateBugSummary(query);

    System.out.println("Translated to JQL: " + jqlQuery);
    return jiraSearchService.searchTickets(jqlQuery);
  }
}
