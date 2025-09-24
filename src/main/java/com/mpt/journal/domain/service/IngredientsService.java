package com.mpt.journal.domain.service;

import com.mpt.journal.domain.entity.Measure;
import com.mpt.journal.domain.model.IngredientModel;
import com.mpt.journal.domain.model.PagedResult;

import java.util.List;

public interface IngredientsService {
    PagedResult<IngredientModel> getIngredientsList(
            int page,
            int pageSize,
            String name,
            Measure measure,
            Boolean shoeDeleted
    );

    PagedResult<IngredientModel> getIngredientsByRecipe(
            int page,
            int pageSize,
            String recipe_ID,
            String name,
            Measure measure,
            Boolean shoeDeleted
    );

    IngredientModel getIngredientByID(String ingredientID);

    IngredientModel addIngredient(IngredientModel ingredient);

    IngredientModel editIngredient(IngredientModel ingredient);

    void deleteIngredient(String ingredientID);

    void deleteIngredients(List<String> ingredientIDs);

    void confirmDeleteIngredient(String ingredientID);

    void confirmDeleteIngredients(List<String> ingredientIDs);
}
