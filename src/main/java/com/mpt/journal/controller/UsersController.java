package com.mpt.journal.controller;

import com.mpt.journal.domain.model.UserModel;
import com.mpt.journal.domain.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


//Основная бизнес-логика нашего проекта
@Controller
public class UsersController {

    @Autowired
    private UsersService usersService;

    @GetMapping("/users")
    public String getUsers(
            Model model,
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(name = "nickname", required = false) String nickname,
            @RequestParam(name = "minRecipesCount", required = false) Integer minRecipesCount,
            @RequestParam(name = "maxRecipesCount", required = false) Integer maxRecipesCount,
            @RequestParam(name = "show_deleted", required = false) Boolean showDeleted
    ) {
        var users = usersService.getUsers(page, pageSize, nickname, minRecipesCount, maxRecipesCount, showDeleted);
        model.addAttribute("users", users);
        model.addAttribute("nickname", nickname);
        model.addAttribute("minRecipesCount", minRecipesCount);
        model.addAttribute("maxRecipesCount", maxRecipesCount);
        model.addAttribute("show_del", showDeleted);
        return "users";
    }

    @PostMapping("/users/add")
    public String addUser(
                             @RequestParam String nickname,
                             @RequestParam String login,
                             @RequestParam String aboutMe,
                             @RequestParam String password) {
        UserModel newUser = new UserModel(login, nickname, aboutMe, password, "test salt");
        usersService.addUser(newUser);
        return "redirect:/users";
    }


    @PostMapping("/users/update")
    public String updateUser(@RequestParam String id,
                             @RequestParam String login,
                             @RequestParam String nickname,
                             @RequestParam String aboutMe,
                             @RequestParam String password,
                             RedirectAttributes attributes) {
        UserModel updated = new UserModel(id, login, nickname, aboutMe, password, "tested salt");
        var result = usersService.editUser(updated);
        if(result == null)
            attributes.addAttribute("error_msg", "Cannot update user. Please check the fields you`ve filled just now.");
        return "redirect:/users";
    }

    @PostMapping("/users/delete")
    public String deleteUser(@RequestParam String id) {
        usersService.deleteUser(id);
        return "redirect:/users";
    }
}
