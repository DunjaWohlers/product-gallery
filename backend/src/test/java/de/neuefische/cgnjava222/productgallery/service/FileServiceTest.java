package de.neuefische.cgnjava222.productgallery.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.cloudinary.utils.ObjectUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FileServiceTest {

    private final Cloudinary cloudinary = mock(Cloudinary.class);

    private final Uploader uploader = mock(Uploader.class);

    @Test
    void addNewImage() throws IOException {
        File file = new File(this.getClass().getClassLoader().getResource("sawIcon.png").getFile());
        System.out.println(file);
        when(cloudinary.uploader()).thenReturn(uploader);
        when(uploader.upload(file, ObjectUtils.emptyMap())).thenReturn(Map.of("url", "ASD", "public_id", "bla"));

        Map<String, String> actual = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        assertThat(actual).isEqualTo(Map.of("url", "ASD", "public_id", "bla"));
    }
}
