package de.neuefische.cgnjava222.productgallery.controller;

import com.cloudinary.Api;
import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithAnonymousUser
    void whenAnonymousAccessLoginLogoutAndInfo() throws Exception {
        mockMvc.perform(get("/api/users/login"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/users/me"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/users/logout"))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void tryToAddProductAsAnonymousUser() throws Exception {
        mockMvc.perform(post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                           "title": "Brett",
                                           "description": "Zum Frühstücken oder sonstiger Verwendung",
                                           "pictureObj": [
                                                {
                                                   "url": "http://res.cloudinary.com/dcnqizhmg/image/upload/v1661501086/equaeqbgdxv9mkfczq1i.jpg",
                                                   "public_id": "equaeqbgdxv9mkfczq1i"
                                                }
                                            ],
                                            "price": 5,
                                            "availableCount": 4
                                        }
                                """)
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }


    @MockBean
    private Cloudinary cloudinary;

    @MockBean
    private Uploader uploader;

    @MockBean
    private Api api;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    @WithAnonymousUser
    void tryToAddImageAsAnonymousUser() throws Exception {
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
        ).thenReturn(Map.of("url", "hi", "publicId", "bla"));

        MockMvc mockMvc
                = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        mockMvc.perform(multipart("/api/image/uploadFile/").file(firstFile).with(csrf()))
                .andExpect(status().isCreated());
    }
}
