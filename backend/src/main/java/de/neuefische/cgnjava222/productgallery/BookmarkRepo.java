package de.neuefische.cgnjava222.productgallery;

import de.neuefische.cgnjava222.productgallery.model.UserBookmarks;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarkRepo extends MongoRepository<UserBookmarks, String> {
}
