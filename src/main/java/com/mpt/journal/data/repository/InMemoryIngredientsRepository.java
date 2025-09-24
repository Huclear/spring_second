package com.mpt.journal.data.repository;

import com.mpt.journal.domain.entity.IngredientEntity;
import com.mpt.journal.domain.entity.Measure;
import com.mpt.journal.domain.repository.IngredientsRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryIngredientsRepository implements IngredientsRepository {
    private List<IngredientEntity> ingredients = new ArrayList<>();

    @Override
    public List<IngredientEntity> getIngredients(String searchedName, Measure searchedMeasureType) {

        return new ArrayList<>(
                ingredients
                        .stream()
                        .filter(ingredient ->
                                (searchedName == null || searchedName.isBlank() || ingredient.getName().equals(searchedName))
                                        && (searchedMeasureType == null || ingredient.getMeasureType() == searchedMeasureType)
                        )
                        .toList()
        );
    }

    @Override
    public List<IngredientEntity> getIngredientByRecipe(String recipeID, Measure searchedMeasureType, String searchedName) {
        return new ArrayList<>(
                ingredients
                        .stream()
                        .filter(ingredient ->
                                (searchedName == null || searchedName.isBlank() || ingredient.getName().equals(searchedName))
                                        && (recipeID == null || ingredient.getRecipe_ID().equals(recipeID))
                                        && (searchedMeasureType == null || ingredient.getMeasureType() == searchedMeasureType)
                        )
                        .toList()
        );
    }

    @Override
    public IngredientEntity getIngredientById(String ingredientID) {
        return ingredients
                .stream()
                .filter(ing ->
                        ing.getId().equals(ingredientID)
                )
                .findFirst()
                .orElse(null);

    }

    @Override
    public IngredientEntity addIngredient(IngredientEntity ingredient) {
        if (ingredients
                .stream()
                .anyMatch(r ->
                        ingredient.getId().equals(r.getId())
                                || (ingredient.getRecipe_ID().equals(r.getRecipe_ID()) && r.getName().equals(ingredient.getName()))
                )
        )
            return null;

        ingredients.add(ingredient);
        return ingredient;
    }

    @Override
    public IngredientEntity editIngredient(IngredientEntity ingredient) {
        IngredientEntity stored = getIngredientById(ingredient.getId());
        if (stored == null)
            return null;

        int index = ingredients.indexOf(stored);
        ingredients.set(index, ingredient);
        return ingredient;
    }

    @Override
    public void deleteIngredient(String ingredientID) {
        IngredientEntity stored = getIngredientById(ingredientID);
        if (stored.getDeleted())
            confirmDeleteIngredient(ingredientID);
        else {
            stored.setDeleted(true);
            editIngredient(stored);
        }
    }

    @Override
    public void deleteIngredients(List<String> ingredientIDs) {
        ingredientIDs.forEach(this::deleteIngredient);
    }

    @Override
    public void confirmDeleteIngredient(String ingredientID) {
        ingredients.removeIf(recipe -> recipe.getId().equals(ingredientID) && recipe.getDeleted());
    }

    @Override
    public void confirmDeleteIngredients(List<String> ingredientIDs) {
        ingredientIDs.forEach(this::confirmDeleteIngredient);
    }
}
