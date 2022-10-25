package de.neuefische.cgnjava222.productgallery.user;

import de.neuefische.cgnjava222.productgallery.UserRepo;
import de.neuefische.cgnjava222.productgallery.model.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AppUserService implements UserDetailsService {
    private final UserRepo userRepo;

    @Override
    public User loadUserByUsername(String username) {
        //    AppUser appUser = userRepo
        //            .findById(username)
        //            .orElseThrow(() ->
        //                    new UsernameNotFoundException("user not found"));

        userRepo.save(new AppUser(
                "frank",
                "$2a$12$cxWwbULyLMRpN0PmVAT.JOAQcQtuPqfy5dBzGVc2i8r14o1CnPGAm")
        );

        return new User(
                "frank",
                "$2a$12$cxWwbULyLMRpN0PmVAT.JOAQcQtuPqfy5dBzGVc2i8r14o1CnPGAm",
                Collections.singletonList(new SimpleGrantedAuthority("ADMIN")));
    }
}
