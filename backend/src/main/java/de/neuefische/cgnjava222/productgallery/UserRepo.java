package de.neuefische.cgnjava222.productgallery;

import de.neuefische.cgnjava222.productgallery.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<AppUser, String> {
}
