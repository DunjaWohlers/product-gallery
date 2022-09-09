package de.neuefische.cgnjava222.productgallery;

import de.neuefische.cgnjava222.productgallery.model.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends MongoRepository<AppUser, String> {
}
