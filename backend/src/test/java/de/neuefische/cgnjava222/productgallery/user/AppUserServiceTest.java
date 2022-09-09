package de.neuefische.cgnjava222.productgallery.user;

import de.neuefische.cgnjava222.productgallery.UserRepo;
import de.neuefische.cgnjava222.productgallery.model.AppUser;
import de.neuefische.cgnjava222.productgallery.model.NewUser;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AppUserServiceTest {

    @Test
    void loadUserByUsernameSucess() {

        UserRepo repo = mock(UserRepo.class);
        AppUserService appUserService = new AppUserService(repo);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String name = "fred";
        String pw = "ABC%%$§";
        String encodedPW = encoder.encode(pw);
        List<SimpleGrantedAuthority> grantedAuthorities = List.of(new SimpleGrantedAuthority("USER"));

        when(repo.findById("fred")).thenReturn(Optional.of(new AppUser(name, encodedPW, List.of("USER"))));

        repo.save(new AppUser(
                name,
                encodedPW,
                List.of("USER")));

        User user = appUserService.loadUserByUsername("fred");

        assertThat(user).isEqualTo(
                new User(name, encodedPW, grantedAuthorities));
    }

    @Test
    void register() {
        UserRepo repo = mock(UserRepo.class);
        BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);
        BCryptPasswordEncoder activeEncoder = new BCryptPasswordEncoder();
        UserService userService = new UserService(encoder, repo);
        String name = "fred";
        String pw = "ABC%%$§";
        String encodedPW = activeEncoder.encode(pw);

        AppUser expected = new AppUser(
                name,
                encodedPW,
                List.of("USER"));

        when(repo.save(expected)).thenReturn(expected);
        when(encoder.encode(pw)).thenReturn(encodedPW);

        AppUser actual = userService.register(new NewUser(name, pw));
        System.out.println(actual);
        System.out.println(expected);
        assertThat(actual).isEqualTo(expected);
    }
}
