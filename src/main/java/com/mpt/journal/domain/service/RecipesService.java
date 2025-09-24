package com.mpt.journal.domain.service;

import com.mpt.journal.domain.model.PagedResult;
import com.mpt.journal.domain.model.RecipeModel;
import com.mpt.journal.domain.model.RecipesIngredientsFiltering;

import java.util.List;

public interface RecipesService {
    PagedResult<RecipeModel> getRecipes(
            int page,
            int pageSize,
            String name,
            List<RecipesIngredientsFiltering> ingredients,
            Boolean showDeleted
    );
    PagedResult<RecipeModel> getRecipesByUser(
            int page,
            int pageSize,
            String userID,
            String name,
            List<RecipesIngredientsFiltering> ingredients,
            Boolean showDeleted
    );
    RecipeModel getRecipeById(String recipeID);
    RecipeModel addRecipe(RecipeModel recipe);
    RecipeModel editRecipe(RecipeModel recipe);
    void deleteRecipe(String recipeID);
    void deleteRecipes(List<String> recipeIDs);
    void confirmDeleteRecipe(String recipeID);
    void confirmDeleteRecipes(List<String> recipeIDs);
}
