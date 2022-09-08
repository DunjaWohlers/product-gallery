package de.neuefische.cgnjava222.productgallery.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
}
