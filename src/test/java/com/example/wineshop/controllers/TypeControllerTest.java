package com.example.wineshop.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebClient
class TypeControllerTest {

    @Autowired
    private WebTestClient webTestClient;
    @Test
    void all() {
        webTestClient.get().
                uri("/types")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/hal+json");

    }

    @Test
    void one() {

        /*
        webTestClient.get().
                uri("/api/type/{id}")
                .exchange().expectStatus().isOk();

         */
    }
}