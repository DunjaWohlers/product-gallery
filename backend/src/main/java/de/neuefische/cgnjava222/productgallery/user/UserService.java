package de.neuefische.cgnjava222.productgallery.user;

import de.neuefische.cgnjava222.productgallery.UserRepo;
import de.neuefische.cgnjava222.productgallery.model.AppUser;
import de.neuefische.cgnjava222.productgallery.model.NewUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;

    public List<String> getAllNames() {
        return userRepo.findAll().stream().map(AppUser::username).toList();
    }

    public AppUser register(NewUser newUser) {
        return userRepo.save(new AppUser(
                newUser.username(),
                passwordEncoder.encode(newUser.password()),
                List.of("USER")));
    }
}
