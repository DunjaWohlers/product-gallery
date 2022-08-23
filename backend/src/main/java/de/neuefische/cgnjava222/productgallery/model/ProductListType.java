package de.neuefische.cgnjava222.productgallery.model;

import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;

public record ProductListType(
        @Id
        @NotNull
        String id,
        @NotNull
        String title,
        @NotNull
        String pictureUrl,
        @NotNull
        Integer price
) {
}
