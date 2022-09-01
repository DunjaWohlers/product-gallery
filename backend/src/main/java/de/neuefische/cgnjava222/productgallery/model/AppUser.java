package de.neuefische.cgnjava222.productgallery.model;

import org.springframework.data.annotation.Id;

public record AppUser(
        @Id String id,
        String username,
        String passwordHash
) {
}
