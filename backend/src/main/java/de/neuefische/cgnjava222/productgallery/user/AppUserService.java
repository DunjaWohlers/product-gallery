package de.neuefische.cgnjava222.productgallery.user;

import de.neuefische.cgnjava222.productgallery.model.AppUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AppUserService implements UserDetailsService {

    private final Map<String, AppUser> appUsers = Map.of(
            "frank", new AppUser(
                    "frank",
                    "$2a$10$e.K27o4YsAM5ZWqj3PMPHeKY.Ho7NzR7pSMZPi.CY16k5ZT1.t7By" // = "frank123"
            )
    );

    public User loadUserByUsername(String username) {
        AppUser appUser = appUsers.get(username);
        return new User(appUser.username(), appUser.password(), List.of(new SimpleGrantedAuthority("ADMIN")));
    }
}
