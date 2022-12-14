package de.neuefische.cgnjava222.productgallery;

import com.cloudinary.Api;
import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.cloudinary.http44.api.Response;
import org.apache.http.HttpVersion;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
class FileIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Cloudinary cloudinary;

    @MockBean
    private Uploader uploader;

    @MockBean
    private Api api;

    @Test
    @WithMockUser(username = "frank", authorities = {"ADMIN", "USER"})
    void uploadFileAsAdmin() throws Exception {
        MockMultipartFile firstFile = new MockMultipartFile(
                "file", "sawIcon.png",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes());
        when(cloudinary.uploader()).thenReturn(uploader);
        when(uploader
                .upload(
                        any(File.class),
                        anyMap()
                )
        ).thenReturn(Map.of("url", "hi", "public_id", "bla"));

        mockMvc.perform(multipart("/api/image/uploadFile/").file(firstFile).with(csrf()))
                .andExpect(status().isCreated());
    }

    @Test
    @WithAnonymousUser
    void tryToAddImageAsAnonymousUser() throws Exception {
        MockMultipartFile firstFile = new MockMultipartFile(
                "file", "sawIcon.png",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes());
        mockMvc.perform(multipart("/api/image/uploadFile/").file(firstFile).with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "frank", authorities = {"ADMIN", "USER"})
    void deleteFile() throws Exception {
        String publicId = "publ";
        when(cloudinary.api()).thenReturn(api);
        when(api
                .deleteResources(
                        anyList(),
                        anyMap()
                ))
                .thenReturn(new Response(
                        new BasicHttpResponse(
                                new BasicStatusLine(
                                        new HttpVersion(3, 4), 4, "bl"
                                )
                        ),
                        Map.of("url", "bla://blub", "public_id", publicId, "deleted", Map.of("publ", "found"))
                ));
        mockMvc.perform(delete("/api/image/delete/" + publicId).with(csrf()))
                .andExpect(status().isNoContent());
    }
}
