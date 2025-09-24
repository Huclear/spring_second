package com.mpt.journal.domain.model;

import com.mpt.journal.domain.entity.IngredientEntity;
import com.mpt.journal.domain.entity.Measure;

public class IngredientModel extends IngredientEntity {
    public IngredientModel(String id, String recipe_ID, LocalizedName name, Double amount, Measure measureType) {
        super(id, recipe_ID, name, amount, measureType);
    }
    public IngredientModel(String recipe_ID, LocalizedName name, Double amount, Measure measureType) {
        super(recipe_ID, name, amount, measureType);
    }
}
