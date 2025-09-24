package com.mpt.journal.domain.model;

import com.mpt.journal.domain.entity.IngredientEntity;
import com.mpt.journal.domain.entity.RecipeEntity;

import java.util.List;

public class RecipeModel extends RecipeEntity {

    private List<IngredientEntity> ingredients;

    public RecipeModel(String id, String user_ID, String name, String description, List<IngredientEntity> ingredients) {
        super(id, user_ID, name, description);
        this.ingredients = ingredients;
    }
    public RecipeModel(String user_ID, String name, String description, List<IngredientEntity> ingredients) {
        super(user_ID, name, description);
        this.ingredients = ingredients;
    }

    public List<IngredientEntity> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientEntity> ingredients) {
        this.ingredients = ingredients;
    }
}
