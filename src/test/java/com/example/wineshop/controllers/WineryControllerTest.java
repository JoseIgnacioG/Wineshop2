package com.example.wineshop.controllers;

import com.example.wineshop.models.Region;
import com.example.wineshop.models.Type;
import com.example.wineshop.models.Winery;
import com.example.wineshop.repositories.TypeRepository;
import com.example.wineshop.repositories.WineryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebClient
class WineryControllerTest {

    @MockBean
    WineryRepository repository;
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void all() {

        webTestClient.get().
                uri("/winerys")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/json");

    }

    @Test
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
    void testGetWineryNotExist()
    {

        webTestClient.get().uri("/api/winery/{id}", 0)
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().valueEquals("Content-Type", "text/plain;charset=UTF-8");
    }
}