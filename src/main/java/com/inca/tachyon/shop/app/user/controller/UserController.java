package com.inca.tachyon.shop.app.user.controller;

import com.inca.tachyon.shop.app.user.entity.User;
import com.inca.tachyon.shop.app.user.form.UserForm;
import com.inca.tachyon.shop.app.user.service.UserService;
import com.inca.tachyon.shop.aspect.LoginUser;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public void create(@Valid @RequestBody UserForm userForm) {
        ModelMapper modelMapper = new ModelMapper();
        User user = modelMapper.map(userForm, User.class);
        userService.insertUser(user);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserForm> profile(@LoginUser User user) {
        UserForm userForm = new UserForm();
        userForm.setEmail(user.getEmail());
        return ResponseEntity.ok(userForm);
    }
}
