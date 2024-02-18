package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("")
    public String printUsers(ModelMap modelMap, Principal principal) {
        modelMap.addAttribute("user", userService.findUserByUsername(principal.getName()));
        modelMap.addAttribute("usersList", userService.findAll());
        modelMap.addAttribute("roles", roleService.getAllUser());
        modelMap.addAttribute("newUser", new User());
        return "admin_panel";
    }

    @PostMapping("/add")
    public String createUser(User user) {
        userService.save(user);
        return "redirect:/admin";
    }


    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute("user") User user, @RequestParam("roles") Long[] rolesId) {
        userService.update(user);

        return "redirect:/admin";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);

        return "redirect:/admin";
    }
}