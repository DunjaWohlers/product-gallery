package de.neuefische.cgnjava222.productgallery.model;

import lombok.*;

import javax.persistence.*;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageInfo {
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    private Long imageId;
    private Long productId;
    private String imageType;
    @Lob
    @Column(
            name = "imageData",
            columnDefinition = "bytea"
    )
    private byte[] imageData;

    public String getUrl() {
        return "/api/image/" + imageId;
    }
}
