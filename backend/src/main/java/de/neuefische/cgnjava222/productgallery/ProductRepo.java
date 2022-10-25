package de.neuefische.cgnjava222.productgallery;

import de.neuefische.cgnjava222.productgallery.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

}
