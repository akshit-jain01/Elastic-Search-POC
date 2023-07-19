package com.elasticSearch.AutoComplete.controller;

import com.elasticSearch.AutoComplete.model.User;
import com.elasticSearch.AutoComplete.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return this.userService.listAll();
    }

    @GetMapping(path = "/search")
    public List<User> searchUsers(@RequestParam String keywords) {
        return this.userService.search(keywords);
    }

}