package de.neuefische.cgnjava222.productgallery.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {

    @GetMapping("/login")
    public String loginEndpoint() {
        return "login";
    }


}
