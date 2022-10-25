package de.neuefische.cgnjava222.productgallery;

import de.neuefische.cgnjava222.productgallery.model.ImageInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageInfoRepo extends JpaRepository<ImageInfo, Long> {

}
