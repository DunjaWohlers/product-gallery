package de.neuefische.cgnjava222.productgallery.controller;

import de.neuefische.cgnjava222.productgallery.model.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    @GetMapping("/login")
    public UserInfo login() {
        return me();
    }

    @GetMapping("/me")
    public UserInfo me() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        List<String> authorities = authentication
                .getAuthorities()
                .stream().map(Object::toString)
                .toList();
        return new UserInfo(name, authorities);
    }

    @GetMapping("/logout")
    public void logout(HttpSession session) {
        session.invalidate();
    }

}
