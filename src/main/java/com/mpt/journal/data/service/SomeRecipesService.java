package com.mpt.journal.data.service;

import com.mpt.journal.data.Paginator;
import com.mpt.journal.domain.entity.IngredientEntity;
import com.mpt.journal.domain.entity.RecipeEntity;
import com.mpt.journal.domain.model.IngredientModel;
import com.mpt.journal.domain.model.PagedResult;
import com.mpt.journal.domain.model.RecipeModel;
import com.mpt.journal.domain.model.RecipesIngredientsFiltering;
import com.mpt.journal.domain.repository.IngredientsRepository;
import com.mpt.journal.domain.repository.RecipesRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SomeRecipesService implements com.mpt.journal.domain.service.RecipesService {
    private final RecipesRepository _recipes;
    private final IngredientsRepository _ingredients;

    public SomeRecipesService(RecipesRepository recipes, IngredientsRepository ingredients) {
        this._recipes = recipes;
        this._ingredients = ingredients;
    }


    @Override
    public PagedResult<RecipeModel> getRecipes(
            int page,
            int pageSize,
            String name, List<RecipesIngredientsFiltering> ingredients,
            Boolean showDeleted) {
        var recipesByName = _recipes.getRecipes().stream().filter(r ->
                r.getDeleted() == (showDeleted == null ? false : showDeleted) &&
                        (name == null ||
                                r.getRecipe_name().toLowerCase().contains(name.toLowerCase()))
        ).toList();

        if (ingredients == null || ingredients.isEmpty()) {
            return Paginator.paginate(recipesByName, page, pageSize)
                    .map(this::getModelForRecipe);
        }

        var response = recipesByName
                .stream().filter(r -> {
                            var ings = _ingredients.getIngredientByRecipe(r.getId(), null, null);
                            return ingredients.stream().allMatch(ingF ->
                                    ings.stream().anyMatch(ing ->
                                            ing.getName().equals(ingF.getIngredientName())
                                                    && (ingF.getCurrentMeasure() == null || ingF.getCurrentMeasure().equals(ing.getMeasureType()))
                                                    && ing.getAmount() >= ingF.getAmountFrom() && ing.getAmount() <= ingF.getAmountTo()
                                    ));
                        }
                )
                .toList();

        return Paginator.paginate(response, page, pageSize)
                .map(this::getModelForRecipe);
    }

    @Override
    public PagedResult<RecipeModel> getRecipesByUser(
            int page,
            int pageSize,
            String userID,
            String name,
            List<RecipesIngredientsFiltering> ingredients,
            Boolean showDeleted) {
        var recipesByName = _recipes.getRecipesByUser(userID).stream().filter(r ->
                r.getDeleted() == (showDeleted == null ? false : showDeleted) &&
                        (name == null ||
                                r.getRecipe_name().toLowerCase().contains(name.toLowerCase()))
        ).toList();

        if (ingredients == null || ingredients.isEmpty()) {
            return Paginator.paginate(recipesByName, page, pageSize)
                    .map(this::getModelForRecipe);
        }

        var response = recipesByName
                .stream().filter(r -> {
                            var ings = _ingredients.getIngredientByRecipe(r.getId(), null, null);
                            return ingredients.stream().allMatch(ingF ->
                                    ings.stream().anyMatch(ing ->
                                            ing.getName().equals(ingF.getIngredientName())
                                                    && (ingF.getCurrentMeasure() == null || ingF.getCurrentMeasure().equals(ing.getMeasureType()))
                                                    && ing.getAmount() >= ingF.getAmountFrom() && ing.getAmount() <= ingF.getAmountTo()
                                    ));
                        }
                )
                .toList();

        return Paginator.paginate(response, page, pageSize)
                .map(this::getModelForRecipe);
    }

    @Override
    public RecipeModel getRecipeById(String recipeID) {
        return getModelForRecipe(_recipes.getRecipeById(recipeID));
    }

    @Override
    public RecipeModel addRecipe(RecipeModel recipe) {
        return getModelForRecipe(_recipes.addRecipe(convertModelToEntity(recipe)));
    }

    @Override
    public RecipeModel editRecipe(RecipeModel recipe) {
        return getModelForRecipe(_recipes.editRecipe(convertModelToEntity(recipe)));
    }

    @Override
    public void deleteRecipe(String recipeID) {
        _recipes.deleteRecipe(recipeID);
    }

    @Override
    public void deleteRecipes(List<String> recipeIDs) {
        _recipes.deleteRecipes(recipeIDs);
    }

    @Override
    public void confirmDeleteRecipe(String recipeID) {
        _recipes.confirmDeleteRecipe(recipeID);
    }

    @Override
    public void confirmDeleteRecipes(List<String> recipeIDs) {
        _recipes.confirmDeleteRecipes(recipeIDs);
    }

    private RecipeModel getModelForRecipe(RecipeEntity entity) {
        if (entity == null)
            return null;
        List<IngredientEntity> ings = _ingredients.getIngredientByRecipe(entity.getId(), null, null);
        return new RecipeModel(
                entity.getId(),
                entity.getUser_ID(),
                entity.getRecipe_name(),
                entity.getRecipe_description(),
                ings
        );
    }

    private RecipeEntity convertModelToEntity(RecipeModel model) {
        return new RecipeEntity(
                model.getId(),
                model.getUser_ID(),
                model.getRecipe_name(),
                model.getRecipe_description()
        );
    }
}
