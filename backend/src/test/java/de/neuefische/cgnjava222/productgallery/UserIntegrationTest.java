package de.neuefische.cgnjava222.productgallery;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getNames() throws Exception {
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"username" : "horst" , "password" : "xyz"}
                        """)
                .with(csrf())
        ).andExpect(status().is(201));

        mockMvc.perform(get("/api/users/"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        """
                                ["horst"]
                                """
                ));
    }

    @Test
    @WithMockUser(username = "frank", authorities = {"ADMIN", "USER"})
    void getBookmarks() throws Exception {
        mockMvc.perform(get("/api/users/bookmarks"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "frank", authorities = {"ADMIN", "USER"})
    void addBookmarks() throws Exception {
        mockMvc.perform(post("/api/users/bookmarks").contentType(MediaType.APPLICATION_JSON)
                .content("""
                        [
                            {
                            "productId" : "bla",
                            "price": 5,
                            "count": 6
                            }
                        ]
                        """).with(csrf())
        ).andExpect(status().isCreated());

    }
}
