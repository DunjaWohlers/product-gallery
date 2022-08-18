package de.neuefische.cgnjava222.productgallery;

import de.neuefische.cgnjava222.productgallery.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends MongoRepository<Product, String> {

}
