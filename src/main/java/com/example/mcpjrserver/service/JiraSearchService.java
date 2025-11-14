package com.example.mcpjrserver.service;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

import org.springframework.stereotype.Service;

@Service
public class JiraSearchService {

//  private final String jiraUrl = "http://localhost:2990/jira/rest/api/2/search";
  private final String jiraUrl = "http://host.docker.internal:2990/jira/rest/api/2/search";
  private final String auth = "admin:admin";

  public String searchTickets(String jqlQuery) {
    try {
      String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(jiraUrl + "?jql=" + URLEncoder.encode(jqlQuery, "UTF-8")))
          .header("Authorization", "Basic " + encodedAuth)
          .header("Accept", "application/json")
          .GET()
          .build();

      HttpClient client = HttpClient.newHttpClient();
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

      return response.body();

    } catch (Exception e) {
      e.printStackTrace();
      return "{\"error\":\"" + e.getMessage() + "\"}";
    }
  }
}
