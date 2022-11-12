package de.neuefische.cgnjava222.productgallery.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewProduct {
    @NotNull
    private String title;
    @NotNull
    @Column(columnDefinition = "TEXT")
    @Size(max = 10000)
    private String description;
    @NotNull
    @OneToMany
    private List<ImageInfo> pictureObj;
}
