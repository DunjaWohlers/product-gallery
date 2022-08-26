package de.neuefische.cgnjava222.productgallery.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.cloudinary.utils.ObjectUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;

import static com.mongodb.assertions.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
public class FileIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Cloudinary cloudinary;

    @MockBean
    private Uploader uploader;

    @Test
    void updateFile1() throws Exception {
        File file = new File(this.getClass().getClassLoader().getResource("sawIcon.png").getFile());
        when(cloudinary.uploader()).thenReturn(uploader);
        when(cloudinary.uploader().upload(file, ObjectUtils.emptyMap())).thenReturn(Map.of("url", "httpBla", "public_id", "id123"));

        MultipartFile multipartFile = new MockMultipartFile("file",
                "sawIcon.png", "text/plain", (byte[]) null);

        System.out.println(multipartFile);
        mockMvc
                .perform(post("/api/image/uploadFile/")
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE).content(
                                String.valueOf(multipartFile))
                ).andExpect(content().json("""
                        {"url": "httpBla",
                        "public_id": "id123"}
                        """));
    }

    @Test
    void updateFile2() throws Exception {
        Resource fileResource = new ClassPathResource(
                "sawIcon.png");

        assertNotNull(fileResource);

        MockMultipartFile firstFile = new MockMultipartFile(
                "file", "sawIcon.png",
                MediaType.MULTIPART_FORM_DATA_VALUE,
                fileResource.getInputStream());
        assertNotNull(firstFile);


        mockMvc.perform(MockMvcRequestBuilders

                        .post("/api/image/uploadFile/", fileResource)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                )
                .andExpect(content().json("""
                        {"url": "httpBla",
                        "public_id": "id123"}
                        """));
    }
}
