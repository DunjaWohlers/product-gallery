package de.neuefische.cgnjava222.productgallery;

import de.neuefische.cgnjava222.productgallery.model.SingleOrder;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends MongoRepository<SingleOrder, String> {
    List<SingleOrder> findAllByUserName(String userName);
}
