package de.neuefische.cgnjava222.productgallery.model;

import java.util.List;

public record UserInfo(
        String name,
        List<String> authorities
) {
}
