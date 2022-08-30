package de.neuefische.cgnjava222.productgallery.service;

import com.cloudinary.Api;
import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.cloudinary.api.ApiResponse;
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
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
class FileIntegrationTest {

    @MockBean
    private Cloudinary cloudinary;

    @MockBean
    private Uploader uploader;

    @MockBean
    private Api api;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    void uploadFile() throws Exception {
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
        ).thenReturn(Map.of("url", "hi", "public_id", "bla"));

        MockMvc mockMvc
                = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(multipart("/api/image/uploadFile/").file(firstFile))
                .andExpect(status().isCreated());
    }

    @Test
    void uploadFileExceptionsFileNameNull() {
        MockMultipartFile nullNamedFile = new MockMultipartFile(
                "file", null,
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes());
        MockMvc mockMvc
                = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Exception exception = assertThrows(Exception.class, () -> {
            mockMvc.perform(multipart("/api/image/uploadFile/").file(nullNamedFile));
        });

        String expectedMessage = "File Upload der Datei: wurde nicht durchgeführt";
        String actualMessage = exception.getMessage();

        assertThat(actualMessage).contains(expectedMessage);
    }

    @Test
    void deleteFile() throws Exception {
        String publicId = "publ";
        when(cloudinary.api()).thenReturn(api);
        ApiResponse response = mock(ApiResponse.class);
        when(api
                .deleteResources(
                        anyList(),
                        anyMap()
                )).thenReturn(response);
        MockMvc mockMvc
                = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(delete("/api/image/delete/" + publicId))
                .andExpect(status().isNoContent());
    }
}
