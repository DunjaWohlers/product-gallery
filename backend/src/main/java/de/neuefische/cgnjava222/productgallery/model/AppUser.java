package de.neuefische.cgnjava222.productgallery.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppUser {
    @Id
    String username;
    String password;
}
