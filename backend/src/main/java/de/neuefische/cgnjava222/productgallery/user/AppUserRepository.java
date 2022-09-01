package de.neuefische.cgnjava222.productgallery.user;

import de.neuefische.cgnjava222.productgallery.model.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AppUserRepository extends MongoRepository
        <AppUser, String> {
}


