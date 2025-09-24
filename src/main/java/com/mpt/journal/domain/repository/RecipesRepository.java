package com.mpt.journal.domain.repository;


import com.mpt.journal.domain.entity.RecipeEntity;

import java.util.List;

public interface RecipesRepository {
    List<RecipeEntity> getRecipes();

    List<RecipeEntity> getRecipesByUser(String userID);

    RecipeEntity getRecipeById(String recipeID);

    RecipeEntity addRecipe(RecipeEntity recipe);

    RecipeEntity editRecipe(RecipeEntity recipe);

    void deleteRecipe(String recipeID);

    void deleteRecipes(List<String> recipeIDs);

    void confirmDeleteRecipe(String recipeID);

    void confirmDeleteRecipes(List<String> recipeIDs);
}
