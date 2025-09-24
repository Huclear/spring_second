package com.mpt.journal.controller;

import com.mpt.journal.domain.entity.Measure;
import com.mpt.journal.domain.model.IngredientModel;
import com.mpt.journal.domain.model.LocalizedName;
import com.mpt.journal.domain.model.RecipeModel;
import com.mpt.journal.domain.service.IngredientsService;
import com.mpt.journal.domain.service.RecipesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        var recipes = _recipes.getRecipes(1, Integer.MAX_VALUE, null, null, false);

        model.addAttribute("ingredients", ingredients);
        model.addAttribute("selected_measure", measure);
        model.addAttribute("measures", Measure.values());
        model.addAttribute("searched_name", name);
        model.addAttribute("selected_recipe", recipeID);
        model.addAttribute("allowed_recipes", recipes);
        return "ingredients";
    }

    @PostMapping("/ingredients/add")
    public String addIngredient(
            @RequestParam(name = "recipe_id") String recipeID,
            @RequestParam(name = "name_eng") String name_en,
            @RequestParam(name = "name_ru") String name_ru,
            @RequestParam(name = "amount") Double amount,
            @RequestParam(name = "measure") Measure measure,
            RedirectAttributes attributes
    ) {

        var ingModel = new IngredientModel(recipeID, new LocalizedName(name_ru, name_en), amount, measure);
        _ingredients.addIngredient(ingModel);

        attributes.addAttribute("recipe_id", recipeID);
        return "redirect:/ingredients";
    }


    @PostMapping("/ingredients/update")
    public String editRecipe(
            Model model,
            @RequestParam(name = "id") String id,
            @RequestParam(name = "recipe_id") String recipeID,
            @RequestParam(name = "name_eng") String name_en,
            @RequestParam(name = "name_ru") String name_ru,
            @RequestParam(name = "amount") Double amount,
            @RequestParam(name = "measure") Measure measure
    ) {
        IngredientModel editedModel = new IngredientModel(id, recipeID, new LocalizedName(name_ru, name_en), amount, measure);
        _ingredients.editIngredient(editedModel);
        return "redirect:/ingredients";
    }

    @PostMapping("/ingredients/delete")
    public String deleteRecipe(
            Model model,
            @RequestParam(name = "id", required = false) String id
    ) {
        _ingredients.deleteIngredient(id);
        return "redirect:/ingredients";
    }
}

