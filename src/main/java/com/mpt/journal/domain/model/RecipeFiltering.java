package com.mpt.journal.domain.model;

import com.mpt.journal.domain.entity.FilterEntity;

import java.util.List;
import java.util.UUID;

public class RecipeFiltering {
    private String name;

    private UUID user_id;

    private Boolean showDeleted;

    private List<FilterEntity> filters;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getUser_id() {
        return user_id;
    }

    public void setUser_id(UUID user_id) {
        this.user_id = user_id;
    }

    public Boolean getShowDeleted() {
        return showDeleted;
    }

    public void setShowDeleted(Boolean showDeleted) {
        this.showDeleted = showDeleted;
    }

    public List<FilterEntity> getFilters() {
        return filters;
    }

    public void setFilters(List<FilterEntity> filters) {
        this.filters = filters;
    }
}
