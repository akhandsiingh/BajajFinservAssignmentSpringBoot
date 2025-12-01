package com.example.webhooksolver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.*;

@Service
public class WebhookService {

    private final RestTemplate restTemplate;
    private final JdbcTemplate jdbcTemplate;
    private final String generateUrl;

    public WebhookService(
            RestTemplate restTemplate,
            JdbcTemplate jdbcTemplate,
            @Value("${app.webhook.generate-url}") String generateUrl
    ) {
        this.restTemplate = restTemplate;
        this.jdbcTemplate = jdbcTemplate;
        this.generateUrl = generateUrl;
    }

    public void executeFlow() {
        System.out.println("🔵 Starting task...");

        // 1. Send details to generateWebhook API
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("name", "John Doe");
        requestBody.put("regNo", "REG12347");
        requestBody.put("email", "john@example.com");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

        Map response = restTemplate.postForObject(generateUrl, entity, Map.class);

        System.out.println("🔵 Response from generateWebhook: " + response);

        String webhookUrl = (String) response.get("webhook");
        String accessToken = (String) response.get("accessToken");

        String finalQuery = FinalQuery.getQuery();
        HttpHeaders webhookHeaders = new HttpHeaders();
        webhookHeaders.setContentType(MediaType.APPLICATION_JSON);
        webhookHeaders.set("Authorization", accessToken);

        Map<String, String> sqlBody = new HashMap<>();
        sqlBody.put("finalQuery", finalQuery);

        HttpEntity<Map<String, String>> sqlEntity = new HttpEntity<>(sqlBody, webhookHeaders);



        System.out.println("Webhook submitted: " + webhookResponse.getStatusCode());
    }
}
