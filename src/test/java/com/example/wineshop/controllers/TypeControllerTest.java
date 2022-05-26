package com.example.wineshop.controllers;

import com.example.wineshop.models.Type;
import com.example.wineshop.repositories.TypeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebClient
class TypeControllerTest {

    @MockBean
    TypeRepository repository;
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void all() {

        webTestClient.get().
                uri("/types")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/json");

    }

    @Test
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
    void testGetTypeNotExist()
    {

        webTestClient.get().uri("/api/type/{id}", 0)
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().valueEquals("Content-Type", "text/plain;charset=UTF-8");
    }
}