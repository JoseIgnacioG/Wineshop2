package com.example.wineshop.controllers;

import com.example.wineshop.models.Type;
import com.example.wineshop.repositories.TypeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class TypeControllerTest {

    @MockBean
    TypeRepository repository;
    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void notAuthenticated() throws Exception {
        webTestClient.get().
                uri("/types")
                .exchange()
                .expectStatus().is3xxRedirection();
    }

    @Test
    @WithMockUser
    void all() {
        webTestClient.get().
                uri("/types")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/json");

    }


    @Test
    @WithMockUser
    void testCreateType() {
        Type type = new Type();
        type.setId(1L);
        type.setName("Test");


        Mockito.when(repository.save(type)).thenReturn(type);

        webTestClient.post()
                .uri("/types")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(type))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isNotEmpty()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.name").isEqualTo("Test");

        Mockito.verify(repository, times(1)).save(type);
    }

    @Test
    @WithMockUser
    void testGetTypeById()
    {
        Type type = new Type();
        type.setId(1l);
        type.setName("Test");

        Mockito
                .when(repository.findById(1L))
                .thenReturn(Optional.of(type));

        webTestClient.get().uri("/api/type/{id}", 1)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isNotEmpty()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.name").isEqualTo("Test");


        Mockito.verify(repository, times(1)).findById(1l);

    }

    @Test
    @WithMockUser
    void testDeleteType()
    {
        Type type = new Type();
        type.setId(1l);
        type.setName("Test");

        Mockito
                .when(repository.findById(1L))
                .thenReturn(Optional.of(type));

        webTestClient.get().uri("/api/type/{id}", 1)
                .exchange()
                .expectStatus().isOk();

    }


    @Test
    @WithMockUser
    void testGetTypeNotExist()
    {

        webTestClient.get().uri("/api/type/{id}", 0)
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().valueEquals("Content-Type", "text/plain;charset=UTF-8");
    }

}