package de.neuefische.cgnjava222.productgallery.model;

import java.util.List;

public record Product(String id,
                      String title,
                      String description,
                      List<String> pictureUrls,
                      Integer price,
                      Integer availableCount
) {
}
