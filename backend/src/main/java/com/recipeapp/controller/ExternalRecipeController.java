package com.recipeapp.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.recipeapp.service.ExternalRecipeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/external-recipes")
@CrossOrigin(origins = "http://localhost:5173")
public class ExternalRecipeController{

    private final ExternalRecipeService externalRecipeService;

    public ExternalRecipeController(
            ExternalRecipeService externalRecipeService) {
        this.externalRecipeService = externalRecipeService;
    }

    @GetMapping("/search")
    public JsonNode search(@RequestParam String query) {
        return externalRecipeService.searchRecipes(query);
    }
}