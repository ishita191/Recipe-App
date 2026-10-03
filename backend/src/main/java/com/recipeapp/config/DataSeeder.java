package com.recipeapp.config;

import com.recipeapp.entity.*;
import com.recipeapp.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;

    @Override
    @Transactional
    public void run(String... args) {
        if (recipeRepository.count() > 0) {
            log.info("Database already seeded, skipping Indian recipe seeding...");
            return;
        }
        log.info("Seeding database with 50 Indian recipes...");
        seedRecipes();
        log.info("Indian recipe seeding complete!");
    }

    private Map<String, Ingredient> ingredientCache = new HashMap<>();

    private Ingredient getOrCreateIngredient(String name) {
        return ingredientCache.computeIfAbsent(name, n ->
                ingredientRepository.findByName(n)
                        .orElseGet(() -> ingredientRepository.save(
                                Ingredient.builder().name(n).category("Other").build()))
        );
    }

    private void seedRecipes() {

        Recipe r1 = Recipe.builder()
                .name("Rajma Chawal")
                .description("Slow-cooked kidney beans in a thick onion-tomato gravy, served over steamed rice. Punjab's ultimate comfort meal.")
                .cuisine("Punjabi").category("Lunch")
                .prepTime(15).cookTime(40).servings(4).difficulty("Easy")
                .imageUrl("https://source.unsplash.com/800x600/?rajma,chawal,indian,curry")
                .caloriesPerServing(380).proteinPerServing(16.0).fiberPerServing(12.0)
                .carbsPerServing(58.0).fatPerServing(8.0).build();
        r1 = recipeRepository.save(r1);
        addIngredients(r1, new String[][]{{"Kidney Beans", "400", "g"}, {"Onion", "2", "whole"}, {"Tomatoes", "3", "whole"}, {"Garlic", "5", "cloves"}, {"Ginger", "1", "tbsp"}, {"Cumin", "1", "tsp"}, {"Garam Masala", "1", "tsp"}, {"Turmeric", "0.5", "tsp"}, {"Olive Oil", "2", "tbsp"}, {"Rice", "300", "g"}, {"Salt", "1", "tsp"}});
        addSteps(r1, new String[]{"Soak kidney beans overnight, then pressure cook with salt and turmeric until soft, about 25 minutes.", "Heat oil, add cumin seeds, then finely chopped onions. Cook until golden brown.", "Add ginger-garlic paste and cook 2 minutes, then add pureed tomatoes and all dry spices.", "Cook the masala until oil separates, about 10 minutes.", "Add cooked rajma along with its water. Simmer 15 minutes, mashing a few beans for thickness.", "Garnish with coriander and serve hot with steamed basmati rice."});

        // 2. Sarson Da Saag with Makki Roti (Punjabi)
        Recipe r2 = Recipe.builder()
                .name("Sarson Da Saag with Makki Roti")
                .description("Slow-cooked mustard greens finished with ghee, paired with cornmeal flatbread. A winter Punjabi classic.")
                .cuisine("Punjabi").category("Dinner")
                .prepTime(20).cookTime(45).servings(4).difficulty("Medium")
                .imageUrl("https://source.unsplash.com/800x600/?sarson,da,saag,makki,roti")
                .caloriesPerServing(340).proteinPerServing(10.0).fiberPerServing(9.0)
                .carbsPerServing(40.0).fatPerServing(16.0).build();
        r2 = recipeRepository.save(r2);
        addIngredients(r2, new String[][]{{"Spinach", "500", "g"}, {"Onion", "1", "whole"}, {"Garlic", "5", "cloves"}, {"Ginger", "1", "tbsp"}, {"Butter", "40", "g"}, {"All-Purpose Flour", "300", "g"}, {"Salt", "1", "tsp"}, {"Red Chili", "2", "whole"}});
        addSteps(r2, new String[]{"Boil mustard greens and spinach together until soft, about 15 minutes.", "Blend the greens coarsely, keeping some texture.", "Heat butter, sauté chopped onion, garlic, ginger, and red chilies until fragrant.", "Add the blended greens back and simmer for 15 minutes, stirring often.", "Meanwhile knead cornmeal with warm water into a soft dough and pat into flatbreads. Cook on a hot griddle with ghee.", "Serve saag topped with a dollop of butter alongside hot makki roti."});

        Recipe r3 = Recipe.builder()
                .name("Chole Bhature")
                .description("Spicy chickpea curry paired with deep-fried fluffy bread. A beloved North Indian weekend indulgence.")
                .cuisine("Punjabi").category("Breakfast")
                .prepTime(15).cookTime(40).servings(4).difficulty("Medium")
                .imageUrl("https://source.unsplash.com/800x600/?chole,bhature,indian,food")
                .caloriesPerServing(560).proteinPerServing(16.0).fiberPerServing(11.0)
                .carbsPerServing(72.0).fatPerServing(22.0).build();
        r3 = recipeRepository.save(r3);
        addIngredients(r3, new String[][]{{"Chickpeas", "400", "g"}, {"Onion", "2", "whole"}, {"Tomatoes", "3", "whole"}, {"Garlic", "4", "cloves"}, {"Ginger", "1", "tbsp"}, {"Garam Masala", "2", "tsp"}, {"Chili Powder", "1", "tsp"}, {"All-Purpose Flour", "300", "g"}, {"Greek Yogurt", "100", "g"}, {"Olive Oil", "3", "tbsp"}, {"Salt", "1", "tsp"}});
        addSteps(r3, new String[]{"Soak and pressure cook chickpeas with salt until soft, about 25 minutes.", "Sauté onions until golden, add ginger-garlic paste and cook 2 minutes.", "Add pureed tomatoes and all spices, cook until oil separates.", "Add cooked chickpeas with some cooking liquid, simmer 15 minutes until thick.", "For bhature, knead flour with yogurt and a little oil into a soft dough, rest 2 hours, roll into ovals and deep fry until puffed and golden.", "Serve hot chole with bhature, sliced onions, and pickle."});

        Recipe r4 = Recipe.builder()
                .name("Amritsari Kulcha")
                .description("Stuffed potato flatbread baked crisp and brushed with butter, an Amritsar street-food staple.")
                .cuisine("Punjabi").category("Breakfast")
                .prepTime(30).cookTime(25).servings(4).difficulty("Medium")
                .imageUrl("https://source.unsplash.com/800x600/?amritsari,kulcha,stuffed,bread")
                .caloriesPerServing(410).proteinPerServing(10.0).fiberPerServing(4.0)
                .carbsPerServing(58.0).fatPerServing(15.0).build();
        r4 = recipeRepository.save(r4);
        addIngredients(r4, new String[][]{{"All-Purpose Flour", "400", "g"}, {"Potato", "3", "whole"}, {"Onion", "1", "whole"}, {"Green Chili", "2", "whole"}, {"Coriander", "1", "tbsp"}, {"Cumin", "1", "tsp"}, {"Butter", "40", "g"}, {"Salt", "1", "tsp"}});
        addSteps(r4, new String[]{"Knead flour with yogurt, salt and a little baking soda into a soft dough. Rest 2 hours.", "Boil and mash potatoes, mix with chopped onion, green chili, coriander, cumin and salt for the filling.", "Divide dough into balls, stuff with potato filling, and roll gently into flatbreads.", "Cook on a hot tawa or bake in a very hot oven until charred spots appear on both sides.", "Brush generously with butter as soon as they come off the heat.", "Serve hot with chole, pickle, and butter."});



        Recipe r6 = Recipe.builder()
                .name("Malai Kofta")
                .description("Deep-fried paneer and potato dumplings floating in a creamy, mildly sweet cashew-tomato gravy.")
                .cuisine("North Indian").category("Dinner")
                .prepTime(30).cookTime(35).servings(4).difficulty("Medium")
                .imageUrl("https://source.unsplash.com/800x600/?malai,kofta,curry")
                .caloriesPerServing(480).proteinPerServing(14.0).fiberPerServing(3.0)
                .carbsPerServing(34.0).fatPerServing(32.0).build();
        r6 = recipeRepository.save(r6);
        addIngredients(r6, new String[][]{{"Paneer", "250", "g"}, {"Potato", "2", "whole"}, {"Tomatoes", "400", "g"}, {"Onion", "1", "whole"}, {"Heavy Cream", "100", "ml"}, {"Garlic", "4", "cloves"}, {"Ginger", "1", "tsp"}, {"All-Purpose Flour", "3", "tbsp"}, {"Garam Masala", "1", "tsp"}, {"Olive Oil", "3", "tbsp"}, {"Salt", "1", "tsp"}});
        addSteps(r6, new String[]{"Mash paneer and boiled potato together with flour and salt into a smooth dough.", "Shape into small balls and deep fry until golden brown. Set aside.", "Sauté onion, garlic and ginger, then add pureed tomatoes and cook 15 minutes.", "Blend the gravy smooth and strain for a silky texture.", "Return to the pan, add cream and garam masala, simmer 5 minutes.", "Place koftas in a serving dish and pour the hot gravy over just before serving."});

        Recipe r7 = Recipe.builder()
                .name("Kadhi Pakora")
                .description("Tangy yogurt-gram flour curry studded with crispy onion fritters. A North Indian household favourite.")
                .cuisine("North Indian").category("Lunch")
                .prepTime(15).cookTime(30).servings(4).difficulty("Easy")
                .imageUrl("https://source.unsplash.com/800x600/?kadhi,pakora,yogurt,curry")
                .caloriesPerServing(320).proteinPerServing(12.0).fiberPerServing(3.0)
                .carbsPerServing(30.0).fatPerServing(18.0).build();
        r7 = recipeRepository.save(r7);
        addIngredients(r7, new String[][]{{"Greek Yogurt", "300", "g"}, {"Onion", "2", "whole"}, {"All-Purpose Flour", "150", "g"}, {"Turmeric", "0.5", "tsp"}, {"Cumin", "1", "tsp"}, {"Mustard Seeds", "0.5", "tsp"}, {"Curry Leaves", "6", "leaves"}, {"Red Chili", "2", "whole"}, {"Olive Oil", "3", "tbsp"}, {"Salt", "1", "tsp"}});
        addSteps(r7, new String[]{"Whisk yogurt with gram flour, turmeric and water into a smooth batter.", "For pakoras, mix sliced onion with gram flour, salt and a splash of water, then deep fry small fritters until golden.", "Heat oil, splutter mustard seeds, cumin, curry leaves and red chilies for the tempering.", "Pour in the yogurt batter and bring to a gentle boil, stirring continuously to avoid curdling.", "Simmer for 20 minutes on low heat until slightly thickened.", "Drop in the pakoras just before serving so they stay crisp."});


        Recipe r8 = Recipe.builder()
                .name("Baingan Bharta")
                .description("Smoky fire-roasted eggplant mashed and cooked with onions, tomatoes and spices.")
                .cuisine("North Indian").category("Lunch")
                .prepTime(10).cookTime(30).servings(3).difficulty("Easy")
                .imageUrl("https://source.unsplash.com/800x600/?baingan,bharta,roasted,eggplant")
                .caloriesPerServing(210).proteinPerServing(5.0).fiberPerServing(8.0)
                .carbsPerServing(22.0).fatPerServing(11.0).build();
        r8 = recipeRepository.save(r8);
        addIngredients(r8, new String[][]{{"Eggplant", "2", "whole"}, {"Onion", "1", "whole"}, {"Tomatoes", "2", "whole"}, {"Garlic", "3", "cloves"}, {"Ginger", "1", "tsp"}, {"Cumin", "1", "tsp"}, {"Turmeric", "0.25", "tsp"}, {"Olive Oil", "2", "tbsp"}, {"Salt", "0.5", "tsp"}, {"Coriander", "1", "tsp"}});
        addSteps(r8, new String[]{"Char the whole eggplant directly over a flame or under a broiler until the skin blisters and flesh softens.", "Peel off the skin and mash the flesh roughly.", "Heat oil, add cumin seeds, then chopped onion and cook until golden.", "Add ginger-garlic, tomatoes, turmeric and salt. Cook until tomatoes break down.", "Stir in the smoky mashed eggplant and cook 10 minutes, mashing further as it cooks.", "Garnish with coriander and serve with roti."});


        Recipe r9 = Recipe.builder()
                .name("Matar Paneer")
                .description("Soft paneer cubes and sweet green peas simmered in a light tomato-onion gravy.")
                .cuisine("North Indian").category("Dinner")
                .prepTime(10).cookTime(25).servings(3).difficulty("Easy")
                .imageUrl("https://source.unsplash.com/800x600/?matar,paneer,curry,peas")
                .caloriesPerServing(360).proteinPerServing(18.0).fiberPerServing(5.0)
                .carbsPerServing(20.0).fatPerServing(22.0).build();
        r9 = recipeRepository.save(r9);
        addIngredients(r9, new String[][]{{"Paneer", "250", "g"}, {"Peas", "200", "g"}, {"Tomatoes", "300", "g"}, {"Onion", "1", "whole"}, {"Garlic", "3", "cloves"}, {"Ginger", "1", "tsp"}, {"Cumin", "1", "tsp"}, {"Garam Masala", "1", "tsp"}, {"Olive Oil", "2", "tbsp"}, {"Salt", "0.5", "tsp"}});
        addSteps(r9, new String[]{"Lightly pan-fry paneer cubes until edges turn golden. Set aside.", "Sauté onion until soft, add ginger-garlic and cook 2 minutes.", "Add pureed tomatoes, cumin, and garam masala. Cook until oil separates.", "Add peas and a splash of water, simmer 10 minutes until peas are tender.", "Add paneer back in and simmer 5 more minutes.", "Garnish with coriander and serve with roti or rice."});


        Recipe r10 = Recipe.builder()
                .name("Shahi Paneer")
                .description("A royal Mughlai-style paneer curry in a rich, mildly sweet cashew and cream gravy.")
                .cuisine("North Indian").category("Dinner")
                .prepTime(15).cookTime(30).servings(4).difficulty("Medium")
                .imageUrl("https://source.unsplash.com/800x600/?shahi,paneer,mughlai,curry")
                .caloriesPerServing(460).proteinPerServing(17.0).fiberPerServing(3.0)
                .carbsPerServing(18.0).fatPerServing(34.0).build();
        r10 = recipeRepository.save(r10);
        addIngredients(r10, new String[][]{{"Paneer", "300", "g"}, {"Onion", "2", "whole"}, {"Tomatoes", "200", "g"}, {"Heavy Cream", "100", "ml"}, {"Garlic", "3", "cloves"}, {"Ginger", "1", "tsp"}, {"Cardamom", "3", "whole"}, {"Garam Masala", "1", "tsp"}, {"Butter", "30", "g"}, {"Salt", "0.5", "tsp"}});
        addSteps(r10, new String[]{"Blanch onions briefly in hot water, then blend with soaked cashews into a smooth paste.", "Melt butter, add cardamom, then the onion-cashew paste. Cook 10 minutes.", "Add pureed tomatoes and garam masala, cook until the raw smell disappears.", "Add cream and a splash of water to loosen the gravy.", "Add paneer cubes and simmer 8 minutes on low heat.", "Finish with a drizzle of cream and serve with naan."});

        Recipe r11 = Recipe.builder()
                .name("Masala Dosa")
                .description("Crisp fermented rice-lentil crepe filled with a spiced potato masala. South India's iconic breakfast.")
                .cuisine("South Indian").category("Breakfast")
                .prepTime(480).cookTime(20).servings(4).difficulty("Medium")
                .imageUrl("https://source.unsplash.com/800x600/?masala,dosa,south,indian")
                .caloriesPerServing(380).proteinPerServing(9.0).fiberPerServing(5.0)
                .carbsPerServing(62.0).fatPerServing(10.0).build();
        r11 = recipeRepository.save(r11);
        addIngredients(r11, new String[][]{{"Rice", "400", "g"}, {"Lentils", "100", "g"}, {"Potato", "4", "whole"}, {"Onion", "1", "whole"}, {"Mustard Seeds", "0.5", "tsp"}, {"Curry Leaves", "8", "leaves"}, {"Turmeric", "0.5", "tsp"}, {"Olive Oil", "2", "tbsp"}, {"Salt", "1", "tsp"}});
        addSteps(r11, new String[]{"Soak rice and lentils separately for 6 hours, then grind into a smooth batter and ferment overnight.", "Boil and mash potatoes coarsely for the filling.", "Heat oil, splutter mustard seeds and curry leaves, then sauté sliced onion until soft.", "Add turmeric and mashed potato, mix well and season with salt.", "Spread a ladle of batter thin on a hot griddle, drizzle oil, and cook until crisp and golden.", "Place the potato masala on one side, fold the dosa, and serve with sambar and coconut chutney."});

        // 12. Idli Sambar (South Indian)
        Recipe r12 = Recipe.builder()
                .name("Idli Sambar")
                .description("Steamed fermented rice cakes served with a tangy, vegetable-laden lentil stew.")
                .cuisine("South Indian").category("Breakfast")
                .prepTime(480).cookTime(25).servings(4).difficulty("Medium")
                .imageUrl("https://source.unsplash.com/800x600/?idli,sambar,south,indian")
                .caloriesPerServing(300).proteinPerServing(11.0).fiberPerServing(8.0)
                .carbsPerServing(52.0).fatPerServing(5.0).build();
        r12 = recipeRepository.save(r12);
        addIngredients(r12, new String[][]{{"Rice", "300", "g"}, {"Lentils", "150", "g"}, {"Lentils", "150", "g"}, {"Carrot", "1", "whole"}, {"Tomatoes", "2", "whole"}, {"Onion", "1", "whole"}, {"Turmeric", "0.5", "tsp"}, {"Mustard Seeds", "0.5", "tsp"}, {"Curry Leaves", "8", "leaves"}, {"Salt", "1", "tsp"}});
        addSteps(r12, new String[]{"Soak rice and lentils, grind into batter, ferment overnight, then steam in idli moulds for 10 minutes.", "Cook toor dal with turmeric until soft and mash lightly.", "Sauté mustard seeds, curry leaves, onion, carrot and tomato in a separate pan.", "Add the tempered vegetables to the cooked dal along with tamarind water and sambar powder.", "Simmer 15 minutes until vegetables are tender and flavours meld.", "Serve hot, fluffy idlis with a generous ladle of sambar and coconut chutney."});

        // 13. Medu Vada (South Indian)
        Recipe r13 = Recipe.builder()
                .name("Medu Vada")
                .description("Crispy, fluffy lentil doughnuts with a golden crust, a South Indian tiffin classic.")
                .cuisine("South Indian").category("Breakfast")
                .prepTime(240).cookTime(20).servings(4).difficulty("Medium")
                .imageUrl("https://source.unsplash.com/800x600/?medu,vada,south,indian")
                .caloriesPerServing(260).proteinPerServing(9.0).fiberPerServing(6.0)
                .carbsPerServing(30.0).fatPerServing(12.0).build();
        r13 = recipeRepository.save(r13);
        addIngredients(r13, new String[][]{{"Lentils", "300", "g"}, {"Onion", "1", "whole"}, {"Ginger", "1", "tsp"}, {"Curry Leaves", "6", "leaves"}, {"Black Pepper", "0.5", "tsp"}, {"Salt", "1", "tsp"}, {"Olive Oil", "500", "ml"}});
        addSteps(r13, new String[]{"Soak urad dal for 4 hours, drain and grind into a thick, fluffy batter with minimal water.", "Fold in chopped onion, ginger, curry leaves, and crushed pepper.", "Wet your hands, shape the batter into small doughnuts with a hole in the center.", "Heat oil to medium-hot and fry the vadas until deep golden and crisp, turning occasionally.", "Drain on paper towels.", "Serve hot with sambar and coconut chutney."});

        // 14. Rava Upma (South Indian)
        Recipe r14 = Recipe.builder()
                .name("Rava Upma")
                .description("A quick, savory semolina porridge tempered with mustard seeds, curry leaves and vegetables.")
                .cuisine("South Indian").category("Breakfast")
                .prepTime(10).cookTime(15).servings(2).difficulty("Easy")
                .imageUrl("https://source.unsplash.com/800x600/?rava,upma,semolina")
                .caloriesPerServing(280).proteinPerServing(6.0).fiberPerServing(3.0)
                .carbsPerServing(42.0).fatPerServing(10.0).build();
        r14 = recipeRepository.save(r14);
        addIngredients(r14, new String[][]{{"Semolina", "200", "g"}, {"Onion", "1", "whole"}, {"Carrot", "1", "whole"}, {"Peas", "50", "g"}, {"Mustard Seeds", "0.5", "tsp"}, {"Curry Leaves", "6", "leaves"}, {"Olive Oil", "2", "tbsp"}, {"Salt", "0.5", "tsp"}});
        addSteps(r14, new String[]{"Dry roast semolina in a pan until lightly golden and aromatic. Set aside.", "Heat oil, splutter mustard seeds and curry leaves.", "Add chopped onion and cook until translucent, then add carrot and peas.", "Add 400ml water and salt, bring to a boil.", "Slowly stir in the roasted semolina, whisking to avoid lumps.", "Cook covered on low heat for 3-4 minutes until fluffy. Serve hot with chutney."});

        // 15. Ven Pongal (South Indian)
        Recipe r15 = Recipe.builder()
                .name("Ven Pongal")
                .description("A creamy, comforting rice and moong dal porridge tempered with ghee, cumin and pepper.")
                .cuisine("South Indian").category("Breakfast")
                .prepTime(10).cookTime(25).servings(3).difficulty("Easy")
                .imageUrl("https://source.unsplash.com/800x600/?ven,pongal,rice,lentil")
                .caloriesPerServing(320).proteinPerServing(9.0).fiberPerServing(4.0)
                .carbsPerServing(54.0).fatPerServing(9.0).build();
        r15 = recipeRepository.save(r15);
        addIngredients(r15, new String[][]{{"Rice", "200", "g"}, {"Lentils", "100", "g"}, {"Black Pepper", "1", "tsp"}, {"Cumin", "1", "tsp"}, {"Ginger", "1", "tsp"}, {"Curry Leaves", "6", "leaves"}, {"Butter", "30", "g"}, {"Salt", "1", "tsp"}});
        addSteps(r15, new String[]{"Dry roast moong dal lightly, then rinse together with rice.", "Pressure cook rice and dal with 4 cups water and salt until very soft and mushy.", "Heat butter, crackle cumin and coarsely crushed pepper.", "Add curry leaves and grated ginger, cook 1 minute.", "Pour the tempering over the cooked rice-dal mixture and mix well.", "Adjust consistency with hot water and serve hot with coconut chutney."});

        // 16. Bisi Bele Bath (South Indian)
        Recipe r16 = Recipe.builder()
                .name("Bisi Bele Bath")
                .description("A spicy Karnataka one-pot rice, lentil and vegetable dish loaded with a distinctive roasted spice blend.")
                .cuisine("South Indian").category("Lunch")
                .prepTime(15).cookTime(35).servings(4).difficulty("Medium")
                .imageUrl("https://source.unsplash.com/800x600/?bisi,bele,bath,karnataka,rice")
                .caloriesPerServing(380).proteinPerServing(12.0).fiberPerServing(8.0)
                .carbsPerServing(58.0).fatPerServing(10.0).build();
        r16 = recipeRepository.save(r16);
        addIngredients(r16, new String[][]{{"Rice", "200", "g"}, {"Lentils", "150", "g"}, {"Carrot", "2", "whole"}, {"Peas", "100", "g"}, {"Beetroot", "1", "whole"}, {"Tomatoes", "2", "whole"}, {"Tamarind", "30", "g"}, {"Cumin", "1", "tsp"}, {"Curry Leaves", "8", "leaves"}, {"Olive Oil", "2", "tbsp"}, {"Salt", "1", "tsp"}});
        addSteps(r16, new String[]{"Cook rice and toor dal together until soft and mushy.", "Boil mixed vegetables until just tender.", "Prepare tamarind extract by soaking pulp in warm water and straining.", "Combine cooked rice-dal, vegetables, tamarind water and bisi bele bath spice powder. Simmer 15 minutes.", "Heat oil, splutter mustard seeds, curry leaves, and pour over the dish.", "Serve hot with a side of crispy papad and ghee."});

       //  (South Indian)
        Recipe r19 = Recipe.builder()
                .name("Coconut Rice")
                .description("Fluffy rice tossed with fresh grated coconut and a crunchy tempering of lentils and curry leaves.")
                .cuisine("South Indian").category("Lunch")
                .prepTime(10).cookTime(15).servings(3).difficulty("Easy")
                .imageUrl("https://source.unsplash.com/800x600/?coconut,rice,south,indian")
                .caloriesPerServing(320).proteinPerServing(5.0).fiberPerServing(3.0)
                .carbsPerServing(48.0).fatPerServing(13.0).build();
        r19 = recipeRepository.save(r19);
        addIngredients(r19, new String[][]{{"Rice", "300", "g"}, {"Mustard Seeds", "0.5", "tsp"}, {"Curry Leaves", "8", "leaves"}, {"Red Chili", "2", "whole"}, {"Ginger", "1", "tsp"}, {"Olive Oil", "2", "tbsp"}, {"Salt", "0.5", "tsp"}});
        addSteps(r19, new String[]{"Cook rice until fluffy and separate, then spread on a tray to cool slightly.", "Heat oil, splutter mustard seeds, curry leaves, and dried red chilies.", "Add grated ginger and lentils, fry until golden.", "Add freshly grated coconut and cook 2 minutes.", "Fold the tempering and coconut through the cooled rice.", "Season with salt and serve warm or at room temperature."});

        // 20. Rasam (South Indian)
        Recipe r20 = Recipe.builder()
                .name("Rasam")
                .description("A thin, peppery, tangy tamarind soup that's the ultimate South Indian comfort food.")
                .cuisine("South Indian").category("Lunch")
                .prepTime(10).cookTime(20).servings(4).difficulty("Easy")
                .imageUrl("https://source.unsplash.com/800x600/?rasam,south,indian,soup")
                .caloriesPerServing(110).proteinPerServing(4.0).fiberPerServing(3.0)
                .carbsPerServing(16.0).fatPerServing(3.0).build();
        r20 = recipeRepository.save(r20);
        addIngredients(r20, new String[][]{{"Tomatoes", "3", "whole"}, {"Lentils", "50", "g"}, {"Tamarind", "20", "g"}, {"Garlic", "3", "cloves"}, {"Black Pepper", "1", "tsp"}, {"Cumin", "1", "tsp"}, {"Curry Leaves", "8", "leaves"}, {"Mustard Seeds", "0.5", "tsp"}, {"Olive Oil", "1", "tbsp"}, {"Salt", "0.5", "tsp"}});
        addSteps(r20, new String[]{"Cook toor dal until soft, then mash lightly.", "Soak tamarind in warm water and extract the juice.", "Combine mashed dal, tamarind water, chopped tomatoes, crushed pepper and cumin. Simmer 10 minutes.", "Heat oil, splutter mustard seeds, curry leaves and crushed garlic.", "Pour the tempering into the simmering rasam.", "Simmer 5 more minutes and serve hot with steamed rice."});

        // 21. Dhokla (Gujarati)
        Recipe r21 = Recipe.builder()
                .name("Dhokla")
                .description("Light, spongy steamed gram flour cakes tempered with mustard seeds and curry leaves.")
                .cuisine("Gujarati").category("Breakfast")
                .prepTime(15).cookTime(25).servings(4).difficulty("Easy")
                .imageUrl("https://source.unsplash.com/800x600/?dhokla,gujarati,steamed,snack")
                .caloriesPerServing(180).proteinPerServing(7.0).fiberPerServing(3.0)
                .carbsPerServing(26.0).fatPerServing(5.0).build();
        r21 = recipeRepository.save(r21);
        addIngredients(r21, new String[][]{{"All-Purpose Flour", "250", "g"}, {"Greek Yogurt", "100", "g"}, {"Ginger", "1", "tsp"}, {"Mustard Seeds", "0.5", "tsp"}, {"Curry Leaves", "8", "leaves"}, {"Sugar", "1", "tbsp"}, {"Olive Oil", "2", "tbsp"}, {"Salt", "0.5", "tsp"}});
        addSteps(r21, new String[]{"Whisk gram flour with yogurt, ginger paste, sugar, salt and water into a smooth, thick batter.", "Rest the batter for 15 minutes, then add fruit salt or baking soda and mix gently until frothy.", "Pour into a greased tray and steam for 15-18 minutes until a toothpick comes out clean.", "Cool slightly, then cut into squares.", "Heat oil, splutter mustard seeds and curry leaves, and pour over the dhokla.", "Garnish with coriander and grated coconut before serving."});

        // 22. Undhiyu (Gujarati)
        Recipe r22 = Recipe.builder()
                .name("Undhiyu")
                .description("A festive Gujarati mixed-vegetable dish slow-cooked with fenugreek dumplings and warming winter spices.")
                .cuisine("Gujarati").category("Dinner")
                .prepTime(30).cookTime(45).servings(5).difficulty("Hard")
                .imageUrl("https://source.unsplash.com/800x600/?undhiyu,gujarati,mixed,vegetable")
                .caloriesPerServing(390).proteinPerServing(10.0).fiberPerServing(11.0)
                .carbsPerServing(48.0).fatPerServing(16.0).build();
        r22 = recipeRepository.save(r22);
        addIngredients(r22, new String[][]{{"Sweet Potato", "2", "whole"}, {"Eggplant", "2", "whole"}, {"Peas", "150", "g"}, {"Potato", "2", "whole"}, {"All-Purpose Flour", "150", "g"}, {"Coriander", "2", "tbsp"}, {"Turmeric", "0.5", "tsp"}, {"Coconut Oil", "3", "tbsp"}, {"Salt", "1", "tsp"}});
        addSteps(r22, new String[]{"Make small dumplings from gram flour, coriander, and spices, then steam or shallow fry until firm.", "Cut all vegetables into large chunks.", "Heat coconut oil in a heavy pot, add whole spices and let them sizzle.", "Add the vegetables and turmeric, tossing well to coat.", "Cover and cook on low heat for 25-30 minutes, stirring occasionally, until vegetables are tender.", "Add the dumplings in the last 10 minutes and gently mix. Serve hot with puri."});

        // 23. Khandvi (Gujarati)
        Recipe r23 = Recipe.builder()
                .name("Khandvi")
                .description("Delicate, tightly rolled gram flour and yogurt spirals, tempered with mustard seeds and coconut.")
                .cuisine("Gujarati").category("Breakfast")
                .prepTime(20).cookTime(15).servings(3).difficulty("Medium")
                .imageUrl("https://source.unsplash.com/800x600/?khandvi,gujarati,rolls")
                .caloriesPerServing(150).proteinPerServing(6.0).fiberPerServing(2.0)
                .carbsPerServing(18.0).fatPerServing(6.0).build();
        r23 = recipeRepository.save(r23);
        addIngredients(r23, new String[][]{{"All-Purpose Flour", "100", "g"}, {"Greek Yogurt", "200", "g"}, {"Ginger", "1", "tsp"}, {"Turmeric", "0.25", "tsp"}, {"Mustard Seeds", "0.5", "tsp"}, {"Curry Leaves", "6", "leaves"}, {"Olive Oil", "1", "tbsp"}, {"Salt", "0.5", "tsp"}});
        addSteps(r23, new String[]{"Whisk gram flour, yogurt, turmeric, ginger paste, salt and water into a lump-free batter.", "Cook the batter on medium heat, stirring constantly, until it thickens into a smooth paste, about 10 minutes.", "Quickly spread a thin layer onto the back of a greased plate or tray. Repeat with remaining batter.", "Once cooled slightly, cut into strips and roll each one tightly.", "Heat oil, splutter mustard seeds and curry leaves, and drizzle over the rolls.", "Garnish with grated coconut and coriander before serving."});

        // 24. Methi Thepla (Gujarati)
        Recipe r24 = Recipe.builder()
                .name("Methi Thepla")
                .description("Soft, spiced fenugreek flatbreads, a Gujarati travel-friendly staple that keeps well for days.")
                .cuisine("Gujarati").category("Breakfast")
                .prepTime(15).cookTime(20).servings(4).difficulty("Easy")
                .imageUrl("https://source.unsplash.com/800x600/?methi,thepla,gujarati,flatbread")
                .caloriesPerServing(220).proteinPerServing(6.0).fiberPerServing(4.0)
                .carbsPerServing(34.0).fatPerServing(7.0).build();
        r24 = recipeRepository.save(r24);
        addIngredients(r24, new String[][]{{"All-Purpose Flour", "300", "g"}, {"Spinach", "100", "g"}, {"Greek Yogurt", "50", "g"}, {"Turmeric", "0.5", "tsp"}, {"Chili Powder", "0.5", "tsp"}, {"Sesame Oil", "2", "tbsp"}, {"Salt", "0.5", "tsp"}});
        addSteps(r24, new String[]{"Combine flour, chopped fenugreek or spinach leaves, yogurt, turmeric, chili powder, oil and salt.", "Knead into a soft dough using a little water as needed.", "Divide into balls and roll each out thinly.", "Cook on a hot griddle, applying a little oil, until golden brown spots appear on both sides.", "Stack the cooked theplas to keep them soft.", "Serve with yogurt, pickle, or a cup of chai."});

        // 25. Gujarati Kadhi (Gujarati)
        Recipe r25 = Recipe.builder()
                .name("Gujarati Kadhi")
                .description("A lightly sweetened, mildly spiced yogurt curry, thinner and sweeter than its Punjabi cousin.")
                .cuisine("Gujarati").category("Lunch")
                .prepTime(10).cookTime(20).servings(4).difficulty("Easy")
                .imageUrl("https://source.unsplash.com/800x600/?gujarati,kadhi,sweet,yogurt,curry")
                .caloriesPerServing(150).proteinPerServing(6.0).fiberPerServing(1.0)
                .carbsPerServing(16.0).fatPerServing(6.0).build();
        r25 = recipeRepository.save(r25);
        addIngredients(r25, new String[][]{{"Greek Yogurt", "300", "g"}, {"All-Purpose Flour", "30", "g"}, {"Ginger", "1", "tsp"}, {"Mustard Seeds", "0.5", "tsp"}, {"Curry Leaves", "6", "leaves"}, {"Sugar", "1", "tbsp"}, {"Olive Oil", "1", "tbsp"}, {"Salt", "0.5", "tsp"}});
        addSteps(r25, new String[]{"Whisk yogurt, gram flour, ginger paste, sugar and salt with water into a smooth, thin mixture.", "Heat oil, splutter mustard seeds and curry leaves.", "Pour in the yogurt mixture and bring to a gentle boil, stirring constantly.", "Simmer on low heat for 12-15 minutes, stirring occasionally to prevent curdling.", "Adjust sweetness and tang to taste.", "Serve hot with khichdi or steamed rice."});

        // 26. Handvo (Gujarati)
        Recipe r26 = Recipe.builder()
                .name("Handvo")
                .description("A savoury baked lentil-rice cake loaded with vegetables and topped with a sesame-curry leaf crust.")
                .cuisine("Gujarati").category("Breakfast")
                .prepTime(480).cookTime(40).servings(4).difficulty("Medium")
                .imageUrl("https://source.unsplash.com/800x600/?handvo,gujarati,baked,cake")
                .caloriesPerServing(260).proteinPerServing(9.0).fiberPerServing(6.0)
                .carbsPerServing(36.0).fatPerServing(9.0).build();
        r26 = recipeRepository.save(r26);
        addIngredients(r26, new String[][]{{"Rice", "150", "g"}, {"Lentils", "150", "g"}, {"Bottle Gourd", "200", "g"}, {"Carrot", "1", "whole"}, {"Ginger", "1", "tsp"}, {"Turmeric", "0.5", "tsp"}, {"Mustard Seeds", "0.5", "tsp"}, {"Curry Leaves", "8", "leaves"}, {"Olive Oil", "3", "tbsp"}, {"Salt", "1", "tsp"}});
        addSteps(r26, new String[]{"Soak rice and mixed lentils together for 6 hours, then grind into a coarse batter and ferment overnight.", "Fold grated bottle gourd, carrot, ginger, turmeric and salt into the fermented batter.", "Grease a baking pan, pour in the batter.", "Heat oil, splutter mustard seeds, sesame seeds and curry leaves, then pour evenly over the batter.", "Bake at 180°C for 35-40 minutes until a skewer comes out clean and the top is golden.", "Cool slightly, cut into squares, and serve with green chutney."});


        // 28. Aloo Posto (Bengali)
        Recipe r28 = Recipe.builder()
                .name("Aloo Posto")
                .description("A simple, earthy Bengali potato dish cooked in a paste of poppy seeds and green chili.")
                .cuisine("Bengali").category("Lunch")
                .prepTime(10).cookTime(20).servings(3).difficulty("Easy")
                .imageUrl("https://source.unsplash.com/800x600/?aloo,posto,bengali,poppy,seed,potato")
                .caloriesPerServing(260).proteinPerServing(6.0).fiberPerServing(4.0)
                .carbsPerServing(34.0).fatPerServing(12.0).build();
        r28 = recipeRepository.save(r28);
        addIngredients(r28, new String[][]{{"Potato", "4", "whole"}, {"Onion", "1", "whole"}, {"Turmeric", "0.5", "tsp"}, {"Green Chili", "2", "whole"}, {"Mustard Oil", "2", "tbsp"}, {"Salt", "0.5", "tsp"}});
        addSteps(r28, new String[]{"Soak poppy seeds in warm water for 20 minutes, then grind into a smooth paste with green chili.", "Cube potatoes and toss with turmeric and salt.", "Heat mustard oil, add potatoes and fry until lightly golden on the edges.", "Add the poppy seed paste and a splash of water. Mix well to coat the potatoes.", "Cover and cook on low heat for 12-15 minutes, stirring occasionally, until potatoes are fully tender.", "Finish with a drizzle of raw mustard oil and serve with steamed rice."});

        // 29. Cholar Dal (Bengali)
        Recipe r29 = Recipe.builder()
                .name("Cholar Dal")
                .description("A slightly sweet Bengali Bengal-gram dal flavoured with coconut and whole garam masala.")
                .cuisine("Bengali").category("Lunch")
                .prepTime(10).cookTime(30).servings(4).difficulty("Easy")
                .imageUrl("https://source.unsplash.com/800x600/?cholar,dal,bengali,lentil")
                .caloriesPerServing(250).proteinPerServing(13.0).fiberPerServing(9.0)
                .carbsPerServing(36.0).fatPerServing(6.0).build();
        r29 = recipeRepository.save(r29);
        addIngredients(r29, new String[][]{{"Chickpeas", "250", "g"}, {"Coconut", "50", "g"}, {"Bay Leaf", "2", "whole"}, {"Cinnamon", "1", "stick"}, {"Cumin", "1", "tsp"}, {"Sugar", "1", "tsp"}, {"Mustard Oil", "2", "tbsp"}, {"Salt", "1", "tsp"}});
        addSteps(r29, new String[]{"Soak split Bengal gram (chana dal) for 30 minutes, then pressure cook with salt until soft but not mushy.", "Heat mustard oil, add bay leaf and cinnamon, let them sizzle.", "Add cumin seeds and thin slices of coconut, fry until golden.", "Add the cooked dal along with a little sugar and simmer 10 minutes.", "Adjust consistency with water and season to taste.", "Serve warm alongside luchi or steamed rice."});


        // 31. Luchi Aloo Dum (Bengali)
        Recipe r31 = Recipe.builder()
                .name("Luchi Aloo Dum")
                .description("Puffy deep-fried flatbreads served with a spiced, deeply flavourful baby potato curry.")
                .cuisine("Bengali").category("Breakfast")
                .prepTime(20).cookTime(30).servings(4).difficulty("Medium")
                .imageUrl("https://source.unsplash.com/800x600/?luchi,aloo,dum,bengali,breakfast")
                .caloriesPerServing(480).proteinPerServing(8.0).fiberPerServing(5.0)
                .carbsPerServing(62.0).fatPerServing(22.0).build();
        r31 = recipeRepository.save(r31);
        addIngredients(r31, new String[][]{{"All-Purpose Flour", "300", "g"}, {"Potato", "500", "g"}, {"Tomatoes", "2", "whole"}, {"Ginger", "1", "tbsp"}, {"Cumin", "1", "tsp"}, {"Turmeric", "0.5", "tsp"}, {"Mustard Oil", "3", "tbsp"}, {"Salt", "1", "tsp"}});
        addSteps(r31, new String[]{"Knead flour with a little oil, salt and water into a firm dough. Rest 20 minutes.", "Boil baby potatoes until just tender, then peel.", "Heat mustard oil, add cumin seeds, ginger paste, and tomatoes. Cook until softened.", "Add turmeric, salt, and the boiled potatoes, along with a little water. Simmer 15 minutes.", "Roll small portions of dough into rounds and deep fry until puffed and golden.", "Serve hot luchis with the potato curry."});

        // 32. Mishti Doi (Bengali)
        Recipe r32 = Recipe.builder()
                .name("Mishti Doi")
                .description("Caramelised sweetened yogurt set until thick and custardy — Bengal's beloved dessert.")
                .cuisine("Bengali").category("Dessert")
                .prepTime(15).cookTime(10).servings(4).difficulty("Easy")
                .imageUrl("https://source.unsplash.com/800x600/?mishti,doi,bengali,sweet,yogurt")
                .caloriesPerServing(220).proteinPerServing(8.0).fiberPerServing(0.0)
                .carbsPerServing(32.0).fatPerServing(6.0).build();
        r32 = recipeRepository.save(r32);
        addIngredients(r32, new String[][]{{"Milk", "1", "L"}, {"Sugar", "150", "g"}, {"Greek Yogurt", "2", "tbsp"}});
        addSteps(r32, new String[]{"Simmer milk on low heat, stirring occasionally, until reduced by a third.", "In a separate pan, caramelise half the sugar until deep amber, then stir into the reduced milk.", "Add remaining sugar and dissolve completely. Cool the milk until just warm.", "Whisk in a spoonful of yogurt as a starter culture.", "Pour into earthen or ceramic pots and let set in a warm place for 6-8 hours.", "Chill before serving."});

        // 33. Puran Poli (Maharashtrian)
        Recipe r33 = Recipe.builder()
                .name("Puran Poli")
                .description("A sweet stuffed flatbread filled with a spiced jaggery-lentil filling, a festive Maharashtrian treat.")
                .cuisine("Maharashtrian").category("Dessert")
                .prepTime(30).cookTime(30).servings(4).difficulty("Medium")
                .imageUrl("https://source.unsplash.com/800x600/?puran,poli,maharashtrian,sweet,flatbread")
                .caloriesPerServing(320).proteinPerServing(8.0).fiberPerServing(5.0)
                .carbsPerServing(58.0).fatPerServing(6.0).build();
        r33 = recipeRepository.save(r33);
        addIngredients(r33, new String[][]{{"All-Purpose Flour", "250", "g"}, {"Lentils", "200", "g"}, {"Sugar", "150", "g"}, {"Cardamom", "0.5", "tsp"}, {"Turmeric", "0.25", "tsp"}, {"Olive Oil", "2", "tbsp"}, {"Salt", "0.25", "tsp"}});
        addSteps(r33, new String[]{"Cook chana dal until very soft, then drain excess water.", "Mash the dal with jaggery or sugar and cook on low heat until thick and dry. Add cardamom powder.", "Knead flour with turmeric, oil and water into a soft, pliable dough. Rest 20 minutes.", "Stuff a portion of dough with the sweet filling and gently roll out into a flatbread.", "Cook on a hot griddle with ghee until golden brown spots appear on both sides.", "Serve warm with a dollop of ghee or a glass of warm milk."});

        // 34. Misal Pav (Maharashtrian)
        Recipe r34 = Recipe.builder()
                .name("Misal Pav")
                .description("A fiery sprouted-lentil curry topped with crunchy farsan, served with soft bread rolls.")
                .cuisine("Maharashtrian").category("Breakfast")
                .prepTime(20).cookTime(30).servings(4).difficulty("Medium")
                .imageUrl("https://source.unsplash.com/800x600/?misal,pav,maharashtrian,spicy")
                .caloriesPerServing(420).proteinPerServing(16.0).fiberPerServing(10.0)
                .carbsPerServing(52.0).fatPerServing(16.0).build();
        r34 = recipeRepository.save(r34);
        addIngredients(r34, new String[][]{{"Lentils", "250", "g"}, {"Onion", "2", "whole"}, {"Tomatoes", "2", "whole"}, {"Garlic", "4", "cloves"}, {"Ginger", "1", "tsp"}, {"Chili Powder", "2", "tsp"}, {"Turmeric", "0.5", "tsp"}, {"Olive Oil", "3", "tbsp"}, {"Bread", "4", "whole"}, {"Salt", "1", "tsp"}});
        addSteps(r34, new String[]{"Sprout moth beans or mixed lentils over 1-2 days, then boil until just tender.", "Sauté onions until deep golden, add ginger-garlic and cook 2 minutes.", "Add tomatoes, chili powder, turmeric, and a robust spice blend. Cook until oil separates.", "Add the boiled sprouts along with their water. Simmer 15 minutes until well flavoured.", "Ladle the curry into bowls and top generously with crunchy sev and chopped onion.", "Serve hot with soft pav rolls on the side."});

        // 35. Vada Pav (Maharashtrian)
        Recipe r35 = Recipe.builder()
                .name("Vada Pav")
                .description("Mumbai's favourite street food — a spiced potato fritter sandwiched in a soft bread roll with chutneys.")
                .cuisine("Maharashtrian").category("Lunch")
                .prepTime(20).cookTime(20).servings(4).difficulty("Easy")
                .imageUrl("https://source.unsplash.com/800x600/?vada,pav,mumbai,street,food")
                .caloriesPerServing(380).proteinPerServing(8.0).fiberPerServing(4.0)
                .carbsPerServing(52.0).fatPerServing(16.0).build();
        r35 = recipeRepository.save(r35);
        addIngredients(r35, new String[][]{{"Potato", "4", "whole"}, {"All-Purpose Flour", "150", "g"}, {"Garlic", "4", "cloves"}, {"Green Chili", "2", "whole"}, {"Mustard Seeds", "0.5", "tsp"}, {"Turmeric", "0.25", "tsp"}, {"Bread", "4", "whole"}, {"Olive Oil", "500", "ml"}, {"Salt", "0.5", "tsp"}});
        addSteps(r35, new String[]{"Boil and mash potatoes with turmeric, mustard seeds, chopped garlic and green chili.", "Shape the potato mixture into small round balls.", "Make a thick gram flour batter and dip each potato ball to coat evenly.", "Deep fry until golden brown and crisp all over.", "Split bread rolls and spread with garlic and tamarind chutneys.", "Sandwich the hot vada inside and serve immediately with fried green chilies."});

        // 36. Pav Bhaji (Maharashtrian)
        Recipe r36 = Recipe.builder()
                .name("Pav Bhaji")
                .description("A buttery, mashed mixed-vegetable curry served with toasted bread rolls. Mumbai's street food icon.")
                .cuisine("Maharashtrian").category("Dinner")
                .prepTime(20).cookTime(30).servings(4).difficulty("Easy")
                .imageUrl("https://source.unsplash.com/800x600/?pav,bhaji,mumbai,street,food")
                .caloriesPerServing(440).proteinPerServing(10.0).fiberPerServing(8.0)
                .carbsPerServing(58.0).fatPerServing(18.0).build();
        r36 = recipeRepository.save(r36);
        addIngredients(r36, new String[][]{{"Potato", "3", "whole"}, {"Cauliflower", "1", "whole"}, {"Peas", "150", "g"}, {"Bell Pepper", "2", "whole"}, {"Tomatoes", "4", "whole"}, {"Onion", "2", "whole"}, {"Butter", "60", "g"}, {"Garlic", "4", "cloves"}, {"Bread", "6", "whole"}, {"Salt", "1", "tsp"}});
        addSteps(r36, new String[]{"Boil potatoes, cauliflower and peas together until very soft.", "Heat butter, sauté chopped onion, garlic and bell pepper until soft.", "Add tomatoes and pav bhaji masala, cook until the mixture turns thick and glossy.", "Add the boiled vegetables and mash everything together with a potato masher.", "Simmer 10 minutes, adding water as needed for a thick, spoonable consistency.", "Toast bread rolls in butter and serve hot alongside the bhaji, topped with more butter and chopped onion."});

        // 37. Sabudana Khichdi (Maharashtrian)
        Recipe r37 = Recipe.builder()
                .name("Sabudana Khichdi")
                .description("A light, peanut-studded tapioca pearl dish, popularly eaten during fasting days.")
                .cuisine("Maharashtrian").category("Breakfast")
                .prepTime(180).cookTime(15).servings(2).difficulty("Easy")
                .imageUrl("https://source.unsplash.com/800x600/?sabudana,khichdi,tapioca,pearls")
                .caloriesPerServing(320).proteinPerServing(6.0).fiberPerServing(3.0)
                .carbsPerServing(48.0).fatPerServing(13.0).build();
        r37 = recipeRepository.save(r37);
        addIngredients(r37, new String[][]{{"Tapioca Pearls", "200", "g"}, {"Peanut Butter", "3", "tbsp"}, {"Potato", "1", "whole"}, {"Green Chili", "2", "whole"}, {"Cumin", "1", "tsp"}, {"Olive Oil", "2", "tbsp"}, {"Salt", "0.5", "tsp"}});
        addSteps(r37, new String[]{"Soak tapioca pearls in just enough water for 4-6 hours until they turn soft and separate.", "Cube and boil the potato until tender.", "Heat oil, add cumin seeds and chopped green chili.", "Add the boiled potato and roasted crushed peanuts, toss well.", "Add the soaked tapioca pearls and salt, mix gently.", "Cook on low heat for 5-7 minutes, stirring occasionally, until pearls turn translucent. Serve hot with a squeeze of lime."});

        // 38. Zunka Bhakar (Maharashtrian)
        Recipe r38 = Recipe.builder()
                .name("Zunka Bhakar")
                .description("A rustic, spiced gram-flour crumble served with thick millet flatbread. Simple Maharashtrian farmhouse food.")
                .cuisine("Maharashtrian").category("Dinner")
                .prepTime(10).cookTime(20).servings(3).difficulty("Easy")
                .imageUrl("https://source.unsplash.com/800x600/?zunka,bhakar,maharashtrian,rustic")
                .caloriesPerServing(280).proteinPerServing(10.0).fiberPerServing(6.0)
                .carbsPerServing(36.0).fatPerServing(11.0).build();
        r38 = recipeRepository.save(r38);
        addIngredients(r38, new String[][]{{"All-Purpose Flour", "150", "g"}, {"Onion", "2", "whole"}, {"Garlic", "3", "cloves"}, {"Mustard Seeds", "0.5", "tsp"}, {"Turmeric", "0.5", "tsp"}, {"Olive Oil", "3", "tbsp"}, {"Salt", "0.5", "tsp"}});
        addSteps(r38, new String[]{"Heat oil, splutter mustard seeds, then add chopped garlic and onion. Cook until golden.", "Add turmeric and a little water, bring to a simmer.", "Gradually sprinkle in gram flour, stirring continuously to avoid lumps.", "Cover and cook on low heat for 10 minutes, stirring occasionally, until the mixture turns crumbly and cooked through.", "Meanwhile knead millet flour with warm water into a firm dough and pat into thick flatbreads. Cook on a hot griddle.", "Serve the zunka hot with bhakar and a side of raw onion."});

        // 39. Dal Baati Churma (Rajasthani)
        Recipe r39 = Recipe.builder()
                .name("Dal Baati Churma")
                .description("Baked wheat dumplings dunked in ghee, served with spiced lentils and a sweet crumbled wheat dessert.")
                .cuisine("Rajasthani").category("Dinner")
                .prepTime(30).cookTime(50).servings(4).difficulty("Hard")
                .imageUrl("https://source.unsplash.com/800x600/?dal,baati,churma,rajasthani")
                .caloriesPerServing(580).proteinPerServing(16.0).fiberPerServing(9.0)
                .carbsPerServing(68.0).fatPerServing(28.0).build();
        r39 = recipeRepository.save(r39);
        addIngredients(r39, new String[][]{{"All-Purpose Flour", "400", "g"}, {"Lentils", "200", "g"}, {"Tomatoes", "2", "whole"}, {"Garlic", "4", "cloves"}, {"Ginger", "1", "tsp"}, {"Cumin", "1", "tsp"}, {"Turmeric", "0.5", "tsp"}, {"Butter", "100", "g"}, {"Sugar", "80", "g"}, {"Salt", "1", "tsp"}});
        addSteps(r39, new String[]{"Knead flour with a little ghee, salt and water into a stiff dough. Shape into round balls.", "Bake or roast the balls (baati) in an oven at 200°C, turning occasionally, until golden and firm, about 30 minutes.", "Cook mixed lentils with turmeric and salt until soft, then temper with cumin, garlic and ginger.", "For churma, crush a few baked baati coarsely and mix with melted ghee and sugar.", "Crack open the hot baatis and douse generously with ghee.", "Serve baati with dal and a portion of sweet churma on the side."});

        // 40. Gatte Ki Sabzi (Rajasthani)
        Recipe r40 = Recipe.builder()
                .name("Gatte Ki Sabzi")
                .description("Steamed gram flour dumplings simmered in a tangy, spiced yogurt gravy. A Rajasthani desert classic.")
                .cuisine("Rajasthani").category("Lunch")
                .prepTime(20).cookTime(30).servings(3).difficulty("Medium")
                .imageUrl("https://source.unsplash.com/800x600/?gatte,ki,sabzi,rajasthani,gram,flour,curry")
                .caloriesPerServing(310).proteinPerServing(11.0).fiberPerServing(4.0)
                .carbsPerServing(32.0).fatPerServing(16.0).build();
        r40 = recipeRepository.save(r40);
        addIngredients(r40, new String[][]{{"All-Purpose Flour", "200", "g"}, {"Greek Yogurt", "200", "g"}, {"Turmeric", "0.5", "tsp"}, {"Cumin", "1", "tsp"}, {"Chili Powder", "1", "tsp"}, {"Mustard Seeds", "0.5", "tsp"}, {"Olive Oil", "3", "tbsp"}, {"Salt", "1", "tsp"}});
        addSteps(r40, new String[]{"Knead gram flour with oil, turmeric and water into a firm dough. Shape into thin logs.", "Boil the logs in water for 10-12 minutes until firm, then slice into pieces.", "Whisk yogurt with gram flour, turmeric, and chili powder into a smooth mixture.", "Heat oil, splutter mustard seeds and cumin, then pour in the yogurt mixture, stirring continuously.", "Add the boiled gatte pieces and simmer 15 minutes on low heat until the gravy thickens.", "Garnish with coriander and serve with steamed rice or roti."});

        // 41. Ker Sangri (Rajasthani)
        Recipe r41 = Recipe.builder()
                .name("Ker Sangri")
                .description("A unique dry curry of desert berries and beans, tangy and packed with bold Rajasthani spices.")
                .cuisine("Rajasthani").category("Lunch")
                .prepTime(480).cookTime(30).servings(3).difficulty("Medium")
                .imageUrl("https://source.unsplash.com/800x600/?ker,sangri,rajasthani,desert,beans")
                .caloriesPerServing(180).proteinPerServing(6.0).fiberPerServing(8.0)
                .carbsPerServing(22.0).fatPerServing(8.0).build();
        r41 = recipeRepository.save(r41);
        addIngredients(r41, new String[][]{{"Ker Berries", "100", "g"}, {"Sangri Beans", "150", "g"}, {"Red Chili", "3", "whole"}, {"Fenugreek", "0.5", "tsp"}, {"Turmeric", "0.5", "tsp"}, {"Mustard Oil", "3", "tbsp"}, {"Salt", "0.5", "tsp"}});
        addSteps(r41, new String[]{"Soak ker and sangri overnight, then boil separately until tender. Drain well.", "Heat mustard oil, add fenugreek seeds and dried red chilies.", "Add the boiled ker-sangri along with turmeric and salt.", "Cook on medium heat for 15-20 minutes, stirring occasionally, until well roasted and dry.", "Adjust seasoning with a splash of raw mango powder for tang.", "Serve as a side with bajra roti."});

        // 42. Laal Maas (Rajasthani)
        Recipe r42 = Recipe.builder()
                .name("Laal Maas")
                .description("A fiery, deep-red Rajasthani mutton curry loaded with dried red chilies and yogurt.")
                .cuisine("Rajasthani").category("Dinner")
                .prepTime(30).cookTime(50).servings(4).difficulty("Hard")
                .imageUrl("https://source.unsplash.com/800x600/?laal,maas,rajasthani,mutton,curry")
                .caloriesPerServing(480).proteinPerServing(34.0).fiberPerServing(2.0)
                .carbsPerServing(10.0).fatPerServing(32.0).build();
        r42 = recipeRepository.save(r42);
        addIngredients(r42, new String[][]{{"Mutton", "700", "g"}, {"Onion", "2", "whole"}, {"Greek Yogurt", "150", "g"}, {"Garlic", "6", "cloves"}, {"Ginger", "1", "tbsp"}, {"Red Chili", "8", "whole"}, {"Cloves", "4", "whole"}, {"Cardamom", "3", "whole"}, {"Mustard Oil", "4", "tbsp"}, {"Salt", "1", "tsp"}});
        addSteps(r42, new String[]{"Marinate mutton in yogurt, salt, and half the ginger-garlic paste for at least 1 hour.", "Heat mustard oil, add whole spices, then sauté onions until deep brown.", "Add remaining ginger-garlic and ground red chilies, cook 3 minutes.", "Add the marinated mutton and sear on high heat for 5-7 minutes.", "Add water, cover, and simmer on low heat for 35-40 minutes until the meat is tender.", "Adjust seasoning and serve with baati or steamed rice."});

        // 43. Bagara Baingan (Hyderabadi)
        Recipe r43 = Recipe.builder()
                .name("Bagara Baingan")
                .description("Baby eggplants simmered in a nutty, tangy tamarind-peanut-sesame gravy. A Hyderabadi wedding staple.")
                .cuisine("Hyderabadi").category("Dinner")
                .prepTime(20).cookTime(30).servings(3).difficulty("Medium")
                .imageUrl("https://source.unsplash.com/800x600/?bagara,baingan,hyderabadi,eggplant")
                .caloriesPerServing(300).proteinPerServing(8.0).fiberPerServing(8.0)
                .carbsPerServing(22.0).fatPerServing(20.0).build();
        r43 = recipeRepository.save(r43);
        addIngredients(r43, new String[][]{{"Eggplant", "400", "g"}, {"Peanut Butter", "3", "tbsp"}, {"Coconut", "30", "g"}, {"Tamarind", "20", "g"}, {"Onion", "1", "whole"}, {"Garlic", "3", "cloves"}, {"Turmeric", "0.5", "tsp"}, {"Olive Oil", "3", "tbsp"}, {"Salt", "0.5", "tsp"}});
        addSteps(r43, new String[]{"Slit baby eggplants into quarters, keeping the stem intact. Shallow fry until softened. Set aside.", "Dry roast peanuts, sesame seeds, and coconut, then grind into a smooth paste with a little water.", "Sauté onion and garlic until golden, then add the ground paste and cook 5 minutes.", "Add tamarind extract, turmeric, and salt, along with a cup of water.", "Add the fried eggplants and simmer 15 minutes on low heat until the gravy thickens and coats them.", "Serve hot with rice or biryani."});

        // 44. Haleem (Hyderabadi)
        Recipe r44 = Recipe.builder()
                .name("Haleem")
                .description("A slow-cooked, pounded meat, lentil and wheat stew, rich and utterly comforting. A Ramzan speciality.")
                .cuisine("Hyderabadi").category("Dinner")
                .prepTime(30).cookTime(120).servings(6).difficulty("Hard")
                .imageUrl("https://source.unsplash.com/800x600/?haleem,hyderabadi,meat,lentil,stew")
                .caloriesPerServing(460).proteinPerServing(26.0).fiberPerServing(6.0)
                .carbsPerServing(42.0).fatPerServing(22.0).build();
        r44 = recipeRepository.save(r44);
        addIngredients(r44, new String[][]{{"Mutton", "500", "g"}, {"Lentils", "150", "g"}, {"Rice", "50", "g"}, {"Onion", "2", "whole"}, {"Ginger", "1", "tbsp"}, {"Garlic", "4", "cloves"}, {"Garam Masala", "1", "tsp"}, {"Butter", "50", "g"}, {"Salt", "1", "tsp"}});
        addSteps(r44, new String[]{"Cook mutton with mixed lentils, rice, and whole spices in plenty of water until everything is very soft, about 1.5 hours.", "Remove bones if any, then pound or blend the mixture until it forms a smooth, thick paste.", "Fry sliced onions in butter until deep golden and crisp for garnish.", "Continue cooking the pounded mixture on low heat, stirring frequently, until it becomes glossy and stretchy.", "Season with garam masala and salt.", "Serve hot topped with fried onions, mint, coriander, and a wedge of lemon."});


        Recipe r45 = Recipe.builder()
                .name("Goan Fish Curry")
                .description("A tangy, coconut-based fish curry with a distinctive kick from Kashmiri chilies and tamarind.")
                .cuisine("Goan").category("Dinner")
                .prepTime(20).cookTime(25).servings(4).difficulty("Medium")
                .imageUrl("https://source.unsplash.com/800x600/?goan,fish,curry,coconut")
                .caloriesPerServing(340).proteinPerServing(28.0).fiberPerServing(3.0)
                .carbsPerServing(14.0).fatPerServing(20.0).build();
        r45 = recipeRepository.save(r45);
        addIngredients(r45, new String[][]{{"Fish", "600", "g"}, {"Coconut Milk", "300", "ml"}, {"Tamarind", "20", "g"}, {"Onion", "1", "whole"}, {"Garlic", "4", "cloves"}, {"Red Chili", "4", "whole"}, {"Turmeric", "0.5", "tsp"}, {"Coconut Oil", "2", "tbsp"}, {"Salt", "1", "tsp"}});
        addSteps(r45, new String[]{"Marinate fish pieces with turmeric and salt.", "Grind soaked red chilies, garlic, and a little coconut into a smooth paste.", "Heat coconut oil, sauté onion until soft, then add the ground paste and cook 5 minutes.", "Add coconut milk, tamarind extract, and a cup of water. Bring to a gentle simmer.", "Slide in the fish pieces and simmer 10-12 minutes without stirring vigorously.", "Serve hot with steamed rice."});

        Recipe r46 = Recipe.builder()
                .name("Prawn Balchao")
                .description("A bold, tangy Goan prawn pickle-curry with vinegar and a fiery red masala.")
                .cuisine("Goan").category("Dinner")
                .prepTime(20).cookTime(25).servings(3).difficulty("Medium")
                .imageUrl("https://source.unsplash.com/800x600/?prawn,balchao,goan,pickle,curry")
                .caloriesPerServing(300).proteinPerServing(24.0).fiberPerServing(2.0)
                .carbsPerServing(10.0).fatPerServing(18.0).build();
        r46 = recipeRepository.save(r46);
        addIngredients(r46, new String[][]{{"Prawns", "500", "g"}, {"Onion", "2", "whole"}, {"Garlic", "6", "cloves"}, {"Ginger", "1", "tbsp"}, {"Red Chili", "6", "whole"}, {"White Vinegar", "3", "tbsp"}, {"Turmeric", "0.5", "tsp"}, {"Coconut Oil", "3", "tbsp"}, {"Salt", "1", "tsp"}});
        addSteps(r46, new String[]{"Clean and devein prawns, then marinate briefly with turmeric and salt.", "Grind soaked red chilies, garlic and ginger into a smooth paste using vinegar instead of water.", "Heat oil, sauté onions until golden, then add the ground paste and cook until oil separates.", "Add prawns and cook on medium heat for 8-10 minutes until just cooked through.", "Adjust tang with more vinegar if needed, and season with salt.", "Serve with steamed rice or as a side pickle-curry with any meal."});

        // 47. Rogan Josh (Kashmiri)
        Recipe r47 = Recipe.builder()
                .name("Rogan Josh")
                .description("A deep red, aromatic Kashmiri mutton curry flavoured with Kashmiri chilies and fennel.")
                .cuisine("Kashmiri").category("Dinner")
                .prepTime(20).cookTime(50).servings(4).difficulty("Hard")
                .imageUrl("https://source.unsplash.com/800x600/?rogan,josh,kashmiri,mutton,curry")
                .caloriesPerServing(460).proteinPerServing(32.0).fiberPerServing(2.0)
                .carbsPerServing(10.0).fatPerServing(30.0).build();
        r47 = recipeRepository.save(r47);
        addIngredients(r47, new String[][]{{"Mutton", "700", "g"}, {"Greek Yogurt", "150", "g"}, {"Garlic", "5", "cloves"}, {"Ginger", "1", "tbsp"}, {"Paprika", "2", "tsp"}, {"Fennel", "1", "tbsp"}, {"Cardamom", "3", "whole"}, {"Cloves", "4", "whole"}, {"Coconut Oil", "3", "tbsp"}, {"Salt", "1", "tsp"}});
        addSteps(r47, new String[]{"Marinate mutton with yogurt and salt for at least 1 hour.", "Heat oil, add whole spices, then sear the mutton pieces on high heat until browned.", "Add ginger-garlic paste, paprika, and ground fennel. Cook for 5 minutes.", "Add water, cover, and simmer on low heat for 40-45 minutes until the mutton is tender.", "Uncover and reduce the gravy to a thick, glossy consistency.", "Garnish with fresh coriander and serve with steamed rice."});


        Recipe r48 = Recipe.builder()
                .name("Dum Aloo Kashmiri")
                .description("Fried baby potatoes simmered in a vibrant, yogurt-based Kashmiri red gravy.")
                .cuisine("Kashmiri").category("Dinner")
                .prepTime(15).cookTime(30).servings(3).difficulty("Medium")
                .imageUrl("https://source.unsplash.com/800x600/?dum,aloo,kashmiri,potato,curry")
                .caloriesPerServing(320).proteinPerServing(6.0).fiberPerServing(5.0)
                .carbsPerServing(38.0).fatPerServing(16.0).build();
        r48 = recipeRepository.save(r48);
        addIngredients(r48, new String[][]{{"Potato", "500", "g"}, {"Greek Yogurt", "150", "g"}, {"Paprika", "2", "tsp"}, {"Fennel", "1", "tsp"}, {"Ginger", "1", "tsp"}, {"Cardamom", "2", "whole"}, {"Coconut Oil", "3", "tbsp"}, {"Salt", "0.5", "tsp"}});
        addSteps(r48, new String[]{"Boil baby potatoes until just tender, peel, and prick lightly with a fork.", "Shallow fry the potatoes until golden on all sides.", "Whisk yogurt with paprika, ground fennel and a little water to prevent curdling.", "Heat oil, add cardamom, then pour in the yogurt mixture, stirring continuously.", "Add the fried potatoes and simmer on low heat for 15 minutes until the gravy thickens.", "Garnish with a pinch of dry ginger powder and serve with rice or naan."});


        Recipe r49 = Recipe.builder()
                .name("Pani Puri")
                .description("Crisp hollow puris filled with spiced potato and dunked in tangy, spicy tamarind-mint water.")
                .cuisine("Street Food").category("Snack")
                .prepTime(30).cookTime(10).servings(4).difficulty("Medium")
                .imageUrl("https://source.unsplash.com/800x600/?pani,puri,indian,street,food")
                .caloriesPerServing(220).proteinPerServing(5.0).fiberPerServing(4.0)
                .carbsPerServing(36.0).fatPerServing(6.0).build();
        r49 = recipeRepository.save(r49);
        addIngredients(r49, new String[][]{{"Semolina", "150", "g"}, {"Potato", "2", "whole"}, {"Chickpeas", "100", "g"}, {"Tamarind", "30", "g"}, {"Mint", "30", "g"}, {"Cumin", "1", "tsp"}, {"Chili Powder", "0.5", "tsp"}, {"Olive Oil", "500", "ml"}, {"Salt", "0.5", "tsp"}});
        addSteps(r49, new String[]{"Knead semolina with a little flour and water into a stiff dough. Rest 20 minutes.", "Roll out thin and cut into small rounds, then deep fry until they puff up into crisp hollow shells.", "Blend mint, coriander and green chili with water for the mint water, and prepare a separate tangy tamarind water.", "Mash boiled potatoes with boiled chickpeas, cumin, chili powder and salt for the filling.", "Crack a small hole in each puri, stuff with the potato-chickpea filling.", "Dunk in the spiced mint or tamarind water and serve immediately."});

        // 50. Gulab Jamun (Dessert)
        Recipe r50 = Recipe.builder()
                .name("Gulab Jamun")
                .description("Soft, syrup-soaked milk-solid dumplings, fried golden and flavoured with cardamom and rose water.")
                .cuisine("Dessert").category("Dessert")
                .prepTime(20).cookTime(25).servings(6).difficulty("Medium")
                .imageUrl("https://source.unsplash.com/800x600/?gulab,jamun,indian,sweet")
                .caloriesPerServing(300).proteinPerServing(5.0).fiberPerServing(0.5)
                .carbsPerServing(48.0).fatPerServing(11.0).build();
        r50 = recipeRepository.save(r50);
        addIngredients(r50, new String[][]{{"Milk Powder", "150", "g"}, {"All-Purpose Flour", "30", "g"}, {"Milk", "60", "ml"}, {"Sugar", "300", "g"}, {"Cardamom", "0.5", "tsp"}, {"Olive Oil", "500", "ml"}});
        addSteps(r50, new String[]{"Combine milk powder, flour and a pinch of baking soda. Bind into a soft dough with milk, without kneading too much.", "Shape into smooth, crack-free small balls.", "Prepare sugar syrup by dissolving sugar in water with cardamom, simmer until slightly sticky.", "Heat oil on medium-low heat and fry the balls slowly, stirring gently, until evenly deep brown.", "Immediately drop the fried balls into the warm sugar syrup.", "Let soak for at least 1 hour before serving so they absorb the syrup and turn soft."});

    }

    private void addIngredients(Recipe recipe, String[][] data) {
        List<RecipeIngredient> list = new ArrayList<>();
        for (String[] row : data) {
            Ingredient ing = getOrCreateIngredient(row[0]);
            list.add(RecipeIngredient.builder()
                    .recipe(recipe).ingredient(ing)
                    .quantity(row[1]).unit(row[2]).optional(false)
                    .build());
        }
        recipe.setIngredients(list);
        recipeRepository.save(recipe);
    }

    private void addSteps(Recipe recipe, String[] steps) {
        List<RecipeStep> list = new ArrayList<>();
        for (int i = 0; i < steps.length; i++) {
            list.add(RecipeStep.builder()
                    .recipe(recipe).stepNumber(i + 1).instruction(steps[i]).build());
        }
        recipe.setSteps(list);
        recipeRepository.save(recipe);
    }
}