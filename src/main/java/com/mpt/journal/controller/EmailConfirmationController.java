package com.mpt.journal.controller;

import com.fasterxml.jackson.databind.ser.impl.UnknownSerializer;
import com.mpt.journal.data.Paginator;
import com.mpt.journal.domain.entity.EmailConfirmationEntity;
import com.mpt.journal.domain.model.PagedResult;
import com.mpt.journal.domain.service.EmailConfirmationsService;
import com.mpt.journal.domain.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.*;

@Controller

public class EmailConfirmationController {
    @Autowired
    private EmailConfirmationsService _emailConfirmations;
    @Autowired
    private UsersService _users;

    @GetMapping("/emailConf")
    public String getEmailConfs(
            Model model,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "perPage", defaultValue = "10") Integer pageSize,
            @RequestParam(name = "user_ids", required = false) Collection<UUID> user_ids,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "date_from", required = false) Date date_sort_from,
            @RequestParam(name = "date_to", required = false) Date date_sort_to,
            @RequestParam(name = "show_deleted", required = false) Boolean showDeleted
    ) {
        PagedResult<EmailConfirmationEntity> emailConfs;
        if (user_ids != null) {
            var confirmations = user_ids.stream().map(_emailConfirmations::getEmailConfirmationsByUser).toList();
            confirmations = confirmations.stream().filter(Objects::nonNull).toList();
            emailConfs = Paginator.paginate(confirmations, page, pageSize);
        } else
            emailConfs = _emailConfirmations.getEmailConfirmationsList(page, pageSize, email, date_sort_from, date_sort_to, showDeleted);

        var allowedUsers = _users.getUsers(1, Integer.MAX_VALUE, null, null, null, null).getValue();
        model.addAttribute("emailConfs", emailConfs);
        model.addAttribute("user_ids", user_ids);
        model.addAttribute("email", email);
        model.addAttribute("date_from", date_sort_from);
        model.addAttribute("date_to", date_sort_to);
        model.addAttribute("show_deleted", showDeleted);
        model.addAttribute("allowedUsers", allowedUsers);

        return "emailConfirmations/emailConfs";
    }

    @GetMapping("/emailConf/add")
    public String addEmailConf(
            Model model
    ) {
        var allowedUsers = _users.getUsers(1, Integer.MAX_VALUE, null, null, null, null).getValue();
        model.addAttribute("allowedUsers", allowedUsers);
        model.addAttribute("emailConf_model", new EmailConfirmationEntity());

        return "emailConfirmations/createEmailConf";
    }

    @PostMapping("/emailConf/add")
    public String postEmailConf(
            Model model,
            @ModelAttribute EmailConfirmationEntity emailConf,
            BindingResult bindingResult
    ) {
        if (!bindingResult.hasErrors())
            _emailConfirmations.addEmailConfirmation(emailConf);

        return "redirect:/emailConf";
    }

    @GetMapping("/emailConf/update/{id}")
    public String editEmailConf(
            Model model,
            @PathVariable UUID id
    ) {
        var confirmation = _emailConfirmations.getEmailConfirmationByID(id);
        var allowedUsers = _users.getUsers(1, Integer.MAX_VALUE, null, null, null, null).getValue();
        model.addAttribute("allowedUsers", allowedUsers);
        model.addAttribute("emailConf_model", confirmation);

        return "emailConfirmations/editEmailConf";
    }

    @PostMapping("/emailConf/update")
    public String updateEmailConf(
            Model model,
            @ModelAttribute EmailConfirmationEntity emailConf,
            BindingResult bindingResult
    ) {
        if (!bindingResult.hasErrors())
            _emailConfirmations.editEmailConfirmation(emailConf);

        return "redirect:/emailConf";
    }

    @GetMapping("/emailConf/delete/{id}")
    public String deleteEmailConf(
            Model model,
            @PathVariable UUID id
    ) {
        _emailConfirmations.deleteEmailConfirmation(id);
        return "redirect:/emailConf";
    }
}
