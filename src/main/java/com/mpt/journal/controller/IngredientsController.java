package com.mpt.journal.controller;

import com.mpt.journal.domain.entity.IngredientEntity;
import com.mpt.journal.domain.model.Measure;
import com.mpt.journal.domain.service.IngredientsService;
import com.mpt.journal.domain.service.RecipesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
public class IngredientsController {

    @Autowired
    private IngredientsService _ingredients;

    @Autowired
    private RecipesService _recipes;

    public IngredientsController(RecipesService recipes, IngredientsService ingredients) {
        _ingredients = ingredients;
        _recipes = recipes;
    }

    @GetMapping("/ingredients/delete/{id}")
    public String deleteIngredient(
            Model model,
            @PathVariable UUID id
    ) {
        _ingredients.deleteIngredient(id);
        return "redirect:/ingredients";
    }

    @GetMapping("/ingredients")
    public String getRecipeIngredients(
            Model model,
            @RequestParam(name = "recipe_id", required = false) String recipeID,
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "measure", required = false) Measure measure,
            @RequestParam(name = "show_deleted", required = false) Boolean showDeleted
    ) {
        var ingredients = recipeID == null ? _ingredients.getIngredientsList(page, pageSize, name, measure, showDeleted) : _ingredients.getIngredientsByRecipe(page, pageSize, recipeID, name, measure, showDeleted);
        var recipes = _recipes.getRecipes(1, Integer.MAX_VALUE, null, null, null, false);

        model.addAttribute("ingredients", ingredients);
        model.addAttribute("selected_measure", measure);
        model.addAttribute("measures", Measure.values());
        model.addAttribute("searched_name", name);
        model.addAttribute("selected_recipe", recipeID);
        model.addAttribute("allowed_recipes", recipes);
        return "ingredients/ingredients";
    }

    @GetMapping("/ingredients/add")
    public String addIngredient(
            Model model
    ) {
        var recipes = _recipes.getRecipes(1, Integer.MAX_VALUE, null, null, null, null);
        model.addAttribute("ingredient_model", new IngredientEntity());
        model.addAttribute("allowed_recipes", recipes.getValue());
        model.addAttribute("measures", Measure.values());
        return "ingredients/createIngredient";
    }


    @PostMapping("/ingredients/add")
    public String addIngredient(
            @ModelAttribute IngredientEntity ingredient,
            BindingResult bindingResult,
            Model model
    ) {

        if (!bindingResult.hasErrors())
            _ingredients.addIngredient(ingredient);
        return "redirect:/ingredients";
    }


    @GetMapping("/ingredients/update/{id}")
    public String editIngredient(
            Model model,
            @PathVariable UUID id
    ) {
        var ingredient = _ingredients.getIngredientByID(id);
        if (ingredient == null)
            return "redirect:/ingredients";

        var recipes = _recipes.getRecipes(1, Integer.MAX_VALUE, null, null, null, null);
        model.addAttribute("ingredient_model", ingredient);
        model.addAttribute("allowed_recipes", recipes.getValue());
        model.addAttribute("measures", Measure.values());
        return "ingredients/editIngredient";
    }

    @PostMapping("/ingredients/update/{id}")
    public String editIngredient(
            Model model,
            @ModelAttribute IngredientEntity ingredient,
            BindingResult bindingResult
    ) {
        if (!bindingResult.hasErrors())
            _ingredients.editIngredient(ingredient);

        return "redirect:/ingredients";
    }
}

