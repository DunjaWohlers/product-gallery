package de.neuefische.cgnjava222.productgallery.controller;

import de.neuefische.cgnjava222.productgallery.model.AppUser;
import de.neuefische.cgnjava222.productgallery.model.NewUser;
import de.neuefische.cgnjava222.productgallery.model.UserInfo;
import de.neuefische.cgnjava222.productgallery.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/login")
    public void login() {
        System.out.println("User " + me().name() + " ist eingeloggt mit den Rollen" + me().authorities());
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

    @GetMapping("/")
    public List<String> getAllNames() {
        return userService.getAllNames();
    }

    @PostMapping()
    public ResponseEntity<String> register(@RequestBody NewUser newUser) {
        AppUser savedUser = userService.register(newUser);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedUser.username());
    }
}
