package de.neuefische.cgnjava222.productgallery.user;

import de.neuefische.cgnjava222.productgallery.model.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {
    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findById(username)
                .orElse(null);
        if (appUser == null)
            return null;
        return new User(appUser.username(), appUser.passwordHash(), Collections.emptyList());
    }
}
