package de.neuefische.cgnjava222.productgallery.model;

import javax.validation.constraints.NotNull;
import java.util.List;

public record NewProduct(
        @NotNull
        String title,
        @NotNull
        String description,
        @NotNull
        List<ImageInfo> pictureObj,
        @NotNull
        Integer price,
        @NotNull
        Integer availableCount
) {
}

