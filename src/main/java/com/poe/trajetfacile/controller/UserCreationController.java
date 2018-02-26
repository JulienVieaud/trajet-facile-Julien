package com.poe.trajetfacile.controller;

import com.poe.trajetfacile.business.service.UserService;
import com.poe.trajetfacile.domain.User;
import com.poe.trajetfacile.form.UserCreationForm;
import com.poe.trajetfacile.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/signup")
public class UserCreationController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    AuthenticationManagerBuilder authenticationManagerBuilder;

    @GetMapping
    public String showForm(UserCreationForm form, @RequestParam(name = "user", required = false) String userId, @ModelAttribute("attr") String attr, Model model) {
        if (userId != null) {
            User user = userRepository.findOne(Long.valueOf(userId));
            model.addAttribute("user", user.getLogin());
        }

        return "/user/signup";
    }

    @PostMapping
    public String save(@Valid UserCreationForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        if (bindingResult.hasErrors()) {
            return "user/signup";
        }

        if (userRepository.findByLogin(form.getLogin()) != null) {
            // on a déjà un utilisateur avec ce login
            model.addAttribute("duplicateLoginError", "Cet utilisateur existe déjà");
            return "user/signup";
        }

        User user = new User();
        user.setLogin(form.getLogin());
        user.setPassword(form.getPassword());
        userService.signup(user);

        redirectAttributes.addAttribute("user", user.getId());
        return "redirect:/signup";
    }

}
