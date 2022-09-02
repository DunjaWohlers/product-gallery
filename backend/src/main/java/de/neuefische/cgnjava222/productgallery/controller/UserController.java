package de.neuefische.cgnjava222.productgallery.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/auth")
public class UserController {
    @GetMapping("/login")
    public void login() {
        System.out.println("Logged in");
    }

    @GetMapping("/me")
    public String me() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @GetMapping("/logout")
    public void logout(HttpSession session) {
        session.invalidate();
        System.out.println("Logged out");
    }
    //@GetMapping("/getToken" )
    //void getToken(@RequestParam String req, @RequestParam String res) {
    //   // CsrfToken token=session.getAttribute("HttpSessionCsrfTokenRepository.CSRF_TOKEN");
    //}

    //@GetMapping("/login")
    //String login() {
    //    System.out.println(getUsername());
    //    return getUsername();
    //}

    //@GetMapping("/me")
    //String getUsername() {
    //    System.out.println("me");
    //    return SecurityContextHolder
    //            .getContext()
    //            .getAuthentication()
    //            .getName();
    //}

    //@GetMapping("/logout")
    //void logout(HttpSession session) {
    //    session.invalidate();
    //}
}
