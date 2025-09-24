package com.mpt.journal.domain.entity;

import java.util.UUID;

public class RecipeEntity {
    private final String id;
    private final String user_ID;
    private String recipe_name;
    private String recipe_description;
    private Boolean deleted = false;

    public RecipeEntity(String id, String user_ID, String name, String description) {
        this.id = id;
        this.user_ID = user_ID;
        recipe_name = name;
        recipe_description = description;
    }

    public RecipeEntity(String user_ID, String name, String description) {
        this(UUID.randomUUID().toString(), user_ID, name, description);
    }

    public String getId() {
        return id;
    }

    public String getUser_ID() {
        return user_ID;
    }

    public String getRecipe_name() {
        return recipe_name;
    }

    public void setRecipe_name(String recipe_name) {
        this.recipe_name = recipe_name;
    }

    public String getRecipe_description() {
        return recipe_description;
    }

    public void setRecipe_description(String recipe_description) {
        this.recipe_description = recipe_description;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
