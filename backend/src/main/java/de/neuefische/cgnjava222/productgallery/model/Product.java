package de.neuefische.cgnjava222.productgallery.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @NotNull(message = "keine Id vorhanden")
    Long id;
    @NotNull(message = "kein Titel vorhanden")
    String title;
    String description;
    @OneToMany(mappedBy = "productId")
    List<ImageInfo> pictureObj;
}
