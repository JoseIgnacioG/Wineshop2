package com.example.wineshop.controllers;

import com.example.wineshop.models.Region;
import com.example.wineshop.models.Type;
import com.example.wineshop.repositories.RegionRepository;
import com.example.wineshop.repositories.TypeRepository;
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
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class RegionControllerTest {

    @MockBean
    RegionRepository repository;
    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void notAuthenticated() throws Exception {
        webTestClient.get().
                uri("/regions")
                .exchange()
                .expectStatus().is3xxRedirection();
    }
    @Test
    @WithMockUser
    void all() {

        webTestClient.get().
                uri("/regions")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/json");
    }

    @Test
    @WithMockUser
    void testCreateRegion() {
        Region region = new Region();
        region.setId(1L);
        region.setName("Test");
        region.setCountry("CountryTest");


        Mockito.when(repository.save(region)).thenReturn(region);

        webTestClient.post()
                .uri("/region")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(region))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isNotEmpty()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.country").isEqualTo("CountryTest");

        Mockito.verify(repository, times(1)).save(region);
    }

    @Test
    @WithMockUser
    void testGetRegionById()
    {
        Region region = new Region();
        region.setId(1L);
        region.setName("Test");
        region.setCountry("CountryTest");

        Mockito
                .when(repository.findById(1L))
                .thenReturn(Optional.of(region));

        webTestClient.get().uri("/api/region/{id}", 1)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isNotEmpty()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.country").isEqualTo("CountryTest");


        Mockito.verify(repository, times(1)).findById(1l);

    }
    @Test
    @WithMockUser
    void testDeleteRegion()
    {
        Region region = new Region();
        region.setId(1L);
        region.setName("Test");
        region.setCountry("CountryTest");

        Mockito
                .when(repository.findById(1L))
                .thenReturn(Optional.of(region));

        webTestClient.get().uri("/api/region/{id}", 1)
                .exchange()
                .expectStatus().isOk();

    }

    @Test
    @WithMockUser
    void testGetRegionNotExist()
    {
        webTestClient.get().uri("/api/region/{id}", 0)
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().valueEquals("Content-Type", "text/plain;charset=UTF-8");
    }
}