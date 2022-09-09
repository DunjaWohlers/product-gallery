package de.neuefische.cgnjava222.productgallery.user;

import de.neuefische.cgnjava222.productgallery.UserRepo;
import de.neuefische.cgnjava222.productgallery.model.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserService implements UserDetailsService {
    private final UserRepo userRepo;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = userRepo
                .findById(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("user not found"));
        List<SimpleGrantedAuthority> grantedAuthorities = appUser.authorities().stream().map(SimpleGrantedAuthority::new).toList();
        return new User(appUser.username(), appUser.password(), grantedAuthorities);
    }
}
