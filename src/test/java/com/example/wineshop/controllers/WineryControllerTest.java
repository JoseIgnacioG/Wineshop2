package com.example.wineshop.controllers;


import com.example.wineshop.models.Region;
import com.example.wineshop.models.Type;
import com.example.wineshop.models.Winery;
import com.example.wineshop.repositories.TypeRepository;
import com.example.wineshop.repositories.WineryRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class WineryControllerTest {

    @MockBean
    WineryRepository repository;
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private MockMvc mvc;

    @Test
    public void notAuthenticated() throws Exception {
        mvc.perform(get("/winerys"))
                .andExpect(status().is3xxRedirection());
    }
    @Test
    @WithMockUser(username = "ben", password = "benspassword", roles = "USER")
    void all() {
        webTestClient.get().
                uri("/winerys")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/json");

    }

    @Test
    public void get_noAuth_returnsRedirectLogin() {
        this.webTestClient.get().uri("/winerys")
                .exchange()
                .expectStatus().is3xxRedirection();
    }

    @Test
    @WithMockUser(username = "ben", password = "benspassword", roles = "USER")
    void testCreateWinery() {
        Winery winery = new Winery();
        winery.setId(1L);
        winery.setName("Test");


            Mockito.when(repository.save(winery)).thenReturn(winery);

        webTestClient.post()
                .uri("/winery")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(winery))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isNotEmpty()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.name").isEqualTo("Test");

        Mockito.verify(repository, times(1)).save(winery);
    }

    @Test
    @WithMockUser(username = "ben", password = "benspassword", roles = "USER")
    void testGetWineryById()
    {
        Winery winery = new Winery();
        winery.setId(1L);
        winery.setName("Test");

        Mockito
                .when(repository.findById(1L))
                .thenReturn(Optional.of(winery));

        webTestClient.get().uri("/api/winery/{id}", 1)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isNotEmpty()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.name").isEqualTo("Test");


        Mockito.verify(repository, times(1)).findById(1l);

    }

    @Test
    @WithMockUser(username = "ben", password = "benspassword", roles = "USER")
    void testDeleteWinery()
    {
        Winery winery = new Winery();
        winery.setId(1L);
        winery.setName("Test");

        Mockito
                .when(repository.findById(1L))
                .thenReturn(Optional.of(winery));

        webTestClient.get().uri("/api/winery/{id}", 1)
                .exchange()
                .expectStatus().isOk();

    }


    @Test
    @WithMockUser(username = "ben", password = "benspassword", roles = "USER")
    void testGetWineryNotExist()
    {

        webTestClient.get().uri("/api/winery/{id}", 0)
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().valueEquals("Content-Type", "text/plain;charset=UTF-8");
    }
}