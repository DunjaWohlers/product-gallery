package de.neuefische.cgnjava222.productgallery.service;

import de.neuefische.cgnjava222.productgallery.UserRepo;
import de.neuefische.cgnjava222.productgallery.model.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;

    public List<String> getAllNames() {
        return userRepo.findAll().stream().map(AppUser::getUsername).toList();
    }
}
