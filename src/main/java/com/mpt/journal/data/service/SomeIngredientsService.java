package com.mpt.journal.data.service;

import com.mpt.journal.data.Paginator;
import com.mpt.journal.domain.entity.IngredientEntity;
import com.mpt.journal.domain.entity.Measure;
import com.mpt.journal.domain.model.IngredientModel;
import com.mpt.journal.domain.model.PagedResult;
import com.mpt.journal.domain.repository.IngredientsRepository;
import com.mpt.journal.domain.service.IngredientsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SomeIngredientsService implements IngredientsService {
    private final IngredientsRepository _ingredients;

    public SomeIngredientsService(IngredientsRepository ingredients) {
        _ingredients = ingredients;
    }

    @Override
    public PagedResult<IngredientModel> getIngredientsList(int page, int pageSize, String name, Measure measure, Boolean showDeleted) {
        var ingredients = _ingredients.getIngredients(name, measure)
                .stream().filter(ing ->
                        ing.getDeleted() == (showDeleted == null ? false : showDeleted)
                )
                .toList();
        return Paginator.paginate(ingredients, page, pageSize)
                .map(this::getModelFromEntity);
    }

    @Override
    public PagedResult<IngredientModel> getIngredientsByRecipe(int page, int pageSize, String recipe_ID, String name, Measure measure, Boolean showDeleted) {
        var ingredients = _ingredients.getIngredientByRecipe(recipe_ID, measure, name)
                .stream().filter(ing ->
                        ing.getDeleted() == (showDeleted == null ? false : showDeleted)
                )
                .toList();
        return Paginator.paginate(ingredients, page, pageSize)
                .map(this::getModelFromEntity);
    }

    @Override
    public IngredientModel getIngredientByID(String ingredientID) {
        return getModelFromEntity(_ingredients.getIngredientById(ingredientID));
    }

    @Override
    public IngredientModel addIngredient(IngredientModel ingredient) {
        return getModelFromEntity(_ingredients.addIngredient(convertModelToEntity(ingredient)));
    }

    @Override
    public IngredientModel editIngredient(IngredientModel ingredient) {
        return getModelFromEntity(_ingredients.editIngredient(convertModelToEntity(ingredient)));
    }

    @Override
    public void deleteIngredient(String ingredientID) {
        _ingredients.deleteIngredient(ingredientID);
    }

    @Override
    public void deleteIngredients(List<String> ingredientIDs) {
        _ingredients.deleteIngredients(ingredientIDs);
    }

    @Override
    public void confirmDeleteIngredient(String ingredientID) {
        _ingredients.confirmDeleteIngredient(ingredientID);
    }

    @Override
    public void confirmDeleteIngredients(List<String> ingredientIDs) {
        _ingredients.confirmDeleteIngredients(ingredientIDs);
    }

    private IngredientModel getModelFromEntity(IngredientEntity entity) {
        return entity == null ? null : new IngredientModel(
                entity.getId(),
                entity.getRecipe_ID(),
                entity.getName(),
                entity.getAmount(),
                entity.getMeasureType()
        );
    }

    private IngredientEntity convertModelToEntity(IngredientModel model) {
        return model == null ? null : new IngredientEntity(
                model.getId(),
                model.getRecipe_ID(),
                model.getName(),
                model.getAmount(),
                model.getMeasureType()
        );
    }
}
