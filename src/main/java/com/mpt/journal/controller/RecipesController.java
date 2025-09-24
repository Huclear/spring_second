package com.mpt.journal.controller;

import com.mpt.journal.domain.entity.Measure;
import com.mpt.journal.domain.entity.RecipeEntity;
import com.mpt.journal.domain.model.RecipeModel;
import com.mpt.journal.domain.model.RecipesIngredientsFiltering;
import com.mpt.journal.domain.service.RecipesService;
import com.mpt.journal.domain.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RecipesController {
    @Autowired
    private UsersService usersService;
    @Autowired
    private RecipesService recipesService;

//    Первая часть говнокода
    private List<RecipesIngredientsFiltering> filtering = new ArrayList<>();

    @GetMapping("/recipes")
    public String getUsers(
            Model model,
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "user_id", required = false) String user_id,
            @RequestParam(name = "show_deleted", required = false) Boolean showDeleted
    ) {

        var recipes = user_id == null || name.isBlank() ? recipesService.getRecipes(page, pageSize, name, filtering, showDeleted)
                : recipesService.getRecipesByUser(page, pageSize, user_id, name, filtering, showDeleted);
        var allowedUsers = usersService.getUsers(1, Integer.MAX_VALUE, null, null, null, false).getValue();
        var searchedUsers = usersService.getUsers(1, Integer.MAX_VALUE, null, 1, null, false).getValue();

        model.addAttribute("recipes", recipes);
        model.addAttribute("name", name);
        model.addAttribute("user_id", user_id);
        model.addAttribute("ingredients", filtering);
        model.addAttribute("measures", Measure.values());
        model.addAttribute("allowedUsers", allowedUsers);
        model.addAttribute("searchedUsers", searchedUsers);

        return "recipes";
    }

    @PostMapping("/recipes/add")
    public String postRecipe(
            Model model,
            @RequestParam(name = "user_id", required = false) String user_id,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "description", required = false) String description
    ){
        RecipeModel newModel = new RecipeModel(user_id, name, description, null);
        recipesService.addRecipe(newModel);
        return "redirect:/recipes";
    }

    @PostMapping("/recipes/edit")
    public String editRecipe(
            Model model,
            @RequestParam(name = "id", required = false) String id,
            @RequestParam(name = "user_id", required = false) String user_id,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "description", required = false) String description
    ){
        RecipeModel newModel = new RecipeModel(id, user_id, name, description, null);
        recipesService.editRecipe(newModel);
        return "redirect:/recipes";
    }

    @PostMapping("/recipes/delete")
    public String deleteRecipe(
            Model model,
            @RequestParam(name = "id", required = false) String id
    ){
        recipesService.deleteRecipe(id);
        return "redirect:/recipes";
    }

    //вторая чатсь говнокода
    @PostMapping("/recipes/addIngrFilter")
    public String addFilteringParam(
            Model model,
            @RequestParam(name = "ingredientName") String ingredientName,
            @RequestParam(name = "measure", required = false) Measure measure,
            @RequestParam(name = "amountFrom", required = false) Double amountFrom,
            @RequestParam(name = "amountTo", required = false) Double amountTo,
            RedirectAttributes attributes) {
        if (filtering.stream().anyMatch(inf ->
                inf.getIngredientName().equals(ingredientName))) {
            attributes.addAttribute("ingredients", filtering);
            return "redirect:/recipes";
        }

        filtering.add(new RecipesIngredientsFiltering(ingredientName, measure, amountFrom, amountTo));
        return "redirect:/recipes";
    }

    @PostMapping("/recipes/removeIngrFilter")
    public String removeFilteringParam(
            Model model,
            @ModelAttribute(name = "removedFilter") int index) {
        if (filtering.size() > index && index >= 0)
            filtering.remove(index);
        return "redirect:/recipes";
    }
}
