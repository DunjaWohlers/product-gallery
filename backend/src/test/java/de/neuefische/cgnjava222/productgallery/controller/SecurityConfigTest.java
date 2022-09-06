package de.neuefische.cgnjava222.productgallery.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class SecurityConfigTest {

    @Autowired
    private WebApplicationContext context;

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

    // @Test
    // @WithAnonymousUser
    // void tryToAddImageAsAnonymousUser() throws Exception {
    //     MockMultipartFile firstFile = new MockMultipartFile(
    //             "file", "sawIcon.png",
    //             MediaType.TEXT_PLAIN_VALUE,
    //             "Hello, World!".getBytes());
    //     assertNotNull(firstFile);

    //     FileService fileService = mock(FileService.class);
    //     MultipartFile[] multipartArray = new MultipartFile[]{firstFile};

    //     when(fileService.uploadPictures(multipartArray)).thenReturn(List.of(new ImageInfo("bla", "blubbb")));

    //     mockMvc.perform(post("/api/image/uploadFile").
    // }
}
