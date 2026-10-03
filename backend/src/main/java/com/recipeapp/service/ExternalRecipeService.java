package com.recipeapp.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ExternalRecipeService {

    private final RestTemplate restTemplate = new RestTemplate();

    public JsonNode searchRecipes(String query) {

        String url = UriComponentsBuilder
                .fromHttpUrl(
                        "https://www.themealdb.com/api/json/v1/1/search.php"
                )
                .queryParam("s", query)
                .toUriString();

        return restTemplate.getForObject(url, JsonNode.class);
    }
}