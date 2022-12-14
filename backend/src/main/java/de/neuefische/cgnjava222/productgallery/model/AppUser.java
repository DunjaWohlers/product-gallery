package de.neuefische.cgnjava222.productgallery.model;

import org.springframework.data.annotation.Id;

import java.util.List;

public record AppUser(
        @Id
        String username,
        String password,
        List<String> authorities
) {
}
