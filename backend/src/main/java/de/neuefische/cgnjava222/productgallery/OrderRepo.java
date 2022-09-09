package de.neuefische.cgnjava222.productgallery;

import de.neuefische.cgnjava222.productgallery.model.UserOrders;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends MongoRepository<UserOrders, String> {
}
