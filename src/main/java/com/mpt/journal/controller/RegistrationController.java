package com.mpt.journal.controller;

import com.mpt.journal.domain.entity.UserEntity;
import com.mpt.journal.domain.model.RegistrationModel;
import com.mpt.journal.domain.model.RoleEnum;
import com.mpt.journal.domain.repository.UsersRepository;
import com.mpt.journal.domain.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Controller
public class RegistrationController {

    @Autowired
    private UsersService _users;

    @Autowired
    private PasswordEncoder _encoder;

    @GetMapping("/registration")
    public String regView(
            Model model
    ) {
        model.addAttribute("request_model", new RegistrationModel());
        return "auth/signUp";
    }

    @PostMapping("/registration")
    public String reg(
            RegistrationModel request,
            BindingResult bindingResult,
            Model model
    ) {
        if (_users.getUserByLogin(request.getLogin()) != null) {
            model.addAttribute("message", "Пользователь с таким логином уже существует");
            return "auth/signUp";
        } else if (bindingResult.hasErrors()) {
            model.addAttribute("message", "Validation Error");
        }
        var user = new UserEntity();
        user.setLogin(request.getLogin());
        user.setNickname(request.getNickname());
        user.setPassword(_encoder.encode(request.getPassword()));
        user.setAboutMe(request.getAboutMe());
        user.setRoles(Collections.singleton(RoleEnum.USER));


        _users.addUser(user);
        return "redirect:/login";
    }
}
