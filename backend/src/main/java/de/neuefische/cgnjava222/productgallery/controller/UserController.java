package de.neuefische.cgnjava222.productgallery.controller;

import de.neuefische.cgnjava222.productgallery.model.UserInfo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @GetMapping("/login")
    public void login() {
        System.getLogger("User" + me().name() + "ist eingeloggt mit den Rollen" + me().authorities());
    }

    @GetMapping("/me")
    public UserInfo me() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        List<String> authorities =
                SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                        .stream().map(Object::toString).toList();
        return new UserInfo(name, authorities);
    }

    @GetMapping("/logout")
    public void logout(HttpSession session) {
        session.invalidate();
    }
}
