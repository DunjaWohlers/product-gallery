package de.neuefische.cgnjava222.productgallery.user;

import de.neuefische.cgnjava222.productgallery.UserRepo;
import de.neuefische.cgnjava222.productgallery.exception.UserNotFoundException;
import de.neuefische.cgnjava222.productgallery.model.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AppUserService implements UserDetailsService {

    private final UserRepo userRepo;

    public User loadUserByUsername(String username) {
        AppUser appUser = userRepo.findById(username).orElseThrow(() -> new UserNotFoundException(username));

        return new User(appUser.username(), appUser.password(), List.of(new SimpleGrantedAuthority("ADMIN")));
    }
}
