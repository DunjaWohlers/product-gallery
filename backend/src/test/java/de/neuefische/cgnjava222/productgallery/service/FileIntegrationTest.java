package de.neuefische.cgnjava222.productgallery.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.util.Map;

import static com.mongodb.assertions.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
class FileIntegrationTest {

    // @Autowired
    // @InjectMocks
    // private MockMvc mockMvc;

    @MockBean
    private Cloudinary cloudinary;

    @MockBean
    private Uploader uploader;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    void updateFile() throws Exception {

        MockMultipartFile firstFile = new MockMultipartFile(
                "file", "sawIcon.png",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes());
        assertNotNull(firstFile);
        when(cloudinary.uploader()).thenReturn(uploader);
        when(uploader
                .upload(
                        any(File.class),
                        anyMap()
                )
        )
                .thenReturn(Map.of("url", "hi", "public_id", "bla"));

        MockMvc mockMvc
                = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(multipart("/api/image/uploadFile/").file(firstFile))
                .andExpect(status().isCreated());
    }


}

