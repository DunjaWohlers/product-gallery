package de.neuefische.cgnjava222.productgallery.model;

import lombok.*;

import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
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
    private String description;
    @NotNull
    @OneToMany
    private List<ImageInfo> pictureObj;
}
