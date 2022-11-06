package de.neuefische.cgnjava222.productgallery.user;

import de.neuefische.cgnjava222.productgallery.UserRepo;
import de.neuefische.cgnjava222.productgallery.model.AppUser;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;
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
        when(repo.findById("fred")).thenReturn(Optional.of(new AppUser(name, encodedPW)));

        repo.save(new AppUser(
                name,
                encodedPW));

        User user = appUserService.loadUserByUsername("fred");

        assertThat(user).isEqualTo(
                new User(name, encodedPW, Collections.emptyList()));
    }
}
