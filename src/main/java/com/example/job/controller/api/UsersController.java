package com.example.job.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.job.model.Users;
import com.example.job.repository.UsersRepository;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/user")
public class UsersController {
    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/info")
    public Users getUserDetails() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return usersRepository.findByEmail(email).orElse(null);
    }

}
