package com.mpt.journal.domain.repository;

import com.mpt.journal.domain.entity.IngredientEntity;
import com.mpt.journal.domain.entity.Measure;

import java.util.List;

public interface IngredientsRepository {
    List<IngredientEntity> getIngredients(String searchedName, Measure searchedMeasureType);
    List<IngredientEntity> getIngredientByRecipe(String recipeID, Measure searchedMeasureType, String searchedName);
    IngredientEntity getIngredientById(String ingredientID);
    IngredientEntity addIngredient(IngredientEntity ingredient);
    IngredientEntity editIngredient(IngredientEntity ingredient);
    void deleteIngredient(String ingredientID);
    void deleteIngredients(List<String> ingredientIDs);
    void confirmDeleteIngredient(String ingredientID);
    void confirmDeleteIngredients(List<String> ingredientIDs);
}
