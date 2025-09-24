package com.mpt.journal.data.repository;

import com.mpt.journal.domain.entity.RecipeEntity;
import com.mpt.journal.domain.repository.RecipesRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryRecipesRepository implements RecipesRepository {
    private List<RecipeEntity> recipes = new ArrayList<>();

    @Override
    public List<RecipeEntity> getRecipes() {
        return new ArrayList<>(recipes);
    }

    @Override
    public List<RecipeEntity> getRecipesByUser(String userID) {
        return new ArrayList<>(
                recipes
                        .stream()
                        .filter(recipe -> recipe.getUser_ID().equals(userID))
                        .toList()
        );
    }

    @Override
    public RecipeEntity getRecipeById(String recipeID) {
        return recipes
                .stream()
                .filter(recipe -> recipe.getId().equals(recipeID))
                .findFirst()
                .orElse(null);
    }

    @Override
    public RecipeEntity addRecipe(RecipeEntity recipe) {
        if (recipes
                .stream()
                .anyMatch(r -> recipe.getId().equals(r.getId()))
        )
            return null;

        recipes.add(recipe);
        return recipe;
    }

    @Override
    public RecipeEntity editRecipe(RecipeEntity recipe) {
        RecipeEntity stored = getRecipeById(recipe.getId());
        if (stored == null)
            return null;

        int index = recipes.indexOf(stored);
        recipes.set(index, recipe);
        return recipe;
    }

    @Override
    public void deleteRecipe(String recipeID) {
        RecipeEntity stored = getRecipeById(recipeID);
        if (stored.getDeleted())
            confirmDeleteRecipe(recipeID);
        else {
            stored.setDeleted(true);
            editRecipe(stored);
        }
    }

    @Override
    public void deleteRecipes(List<String> recipeIDs) {
        recipeIDs.forEach(this::deleteRecipe);
    }

    @Override
    public void confirmDeleteRecipe(String recipeID) {
        recipes.removeIf(recipe -> recipe.getId().equals(recipeID) && recipe.getDeleted());
    }

    @Override
    public void confirmDeleteRecipes(List<String> recipeIDs) {
        recipeIDs.forEach(this::confirmDeleteRecipe);
    }
}
