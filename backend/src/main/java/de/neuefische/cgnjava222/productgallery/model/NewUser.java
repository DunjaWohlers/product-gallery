package de.neuefische.cgnjava222.productgallery.model;

import java.util.List;

public record NewUser(
        String username,
        String password,
        List<OrderItem> bookmarks
) {
}
