package com.mpt.journal.domain.entity;

import com.mpt.journal.domain.model.LocalizedName;

import java.util.UUID;

public class IngredientEntity {
    private final String id;
    private final String recipe_ID;
    private LocalizedName name;
    private Double amount;
    private Measure measureType;
    private Boolean deleted = false;

    public IngredientEntity(String id, String recipe_ID, LocalizedName name, Double amount, Measure measureType) {
        this.id = id;
        this.recipe_ID = recipe_ID;
        this.name = name;
        this.amount = amount;
        this.measureType = measureType;
    }
    public IngredientEntity(String recipe_ID, LocalizedName name, Double amount, Measure measureType) {
        this(UUID.randomUUID().toString(), recipe_ID, name, amount, measureType);
    }

    public String getId() {
        return id;
    }

    public String getRecipe_ID() {
        return recipe_ID;
    }

    public LocalizedName getName() {
        return name;
    }

    public void setName(LocalizedName name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Measure getMeasureType() {
        return measureType;
    }

    public void setMeasureType(Measure measureType) {
        this.measureType = measureType;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
