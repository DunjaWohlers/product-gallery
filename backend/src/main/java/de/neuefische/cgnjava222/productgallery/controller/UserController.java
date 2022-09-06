package de.neuefische.cgnjava222.productgallery.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @GetMapping("/login")
    public void login() {
        System.getLogger("User" + me().get("name") + "ist eingeloggt mit den Rollen" + me().get("authorities"));
    }

    @GetMapping("/me")
    public Map<String, Object> me() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        List<String> authorities =
                SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                        .stream().map(Object::toString).toList();
        return Map.of("name", name, "authorities", authorities);
    }

    @GetMapping("/logout")
    public void logout(HttpSession session) {
        session.invalidate();
    }
}
