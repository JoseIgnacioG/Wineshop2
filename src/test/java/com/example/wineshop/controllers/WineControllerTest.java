package com.example.wineshop.controllers;

import com.example.wineshop.models.Region;
import com.example.wineshop.models.Type;
import com.example.wineshop.models.Wine;
import com.example.wineshop.models.Winery;
import com.example.wineshop.repositories.WineRepository;
import com.example.wineshop.repositories.WineryRepository;
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
class WineControllerTest {
    @MockBean
    WineRepository repository;
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private MockMvc mvc;

    @Test
    public void notAuthenticated() throws Exception {
        mvc.perform(get("/wines"))
                .andExpect(status().is3xxRedirection());
    }
    @Test
    @WithMockUser(username = "ben", password = "benspassword", roles = "USER")
    void all() {

        webTestClient.get().
                uri("/wines")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/json");

    }

    @Test
    @WithMockUser(username = "ben", password = "benspassword", roles = "USER")
    void testCreateWine() {

        Winery winery = new Winery();
        winery.setId(1L);
        winery.setName("Test");

        Type type = new Type();
        type.setId(1L);
        type.setName("Test");

        Region region = new Region();
        region.setId(1L);
        region.setName("Test");
        region.setCountry("CountryTest");

        Wine wine = new Wine();
        wine.setName("Test");
        wine.setId(1L);
        wine.setAcidity(3);
        wine.setBody(5);
        wine.setPrice(286);
        wine.setRating(4);
        wine.setNum_reviews(40);
        wine.setYear(2005);
        wine.setWinery(winery);
        wine.setRegion(region);
        wine.setType(type);

        Mockito.when(repository.save(wine)).thenReturn(wine);

        webTestClient.post()
                .uri("/wines")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(wine))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isNotEmpty()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.name").isEqualTo("Test")
                .jsonPath("$.year").isEqualTo(2005)
                .jsonPath("$.num_reviews").isEqualTo(40)
                .jsonPath("$.rating").isEqualTo(4)
                .jsonPath("$.price").isEqualTo(286)
                .jsonPath("$.body").isEqualTo(5)
                .jsonPath("$.acidity").isEqualTo(3)
                .jsonPath("$.winery").isNotEmpty()
                .jsonPath("$.region").isNotEmpty()
                .jsonPath("$.type").isNotEmpty();

        Mockito.verify(repository, times(1)).save(wine);
    }

    @Test
    @WithMockUser(username = "ben", password = "benspassword", roles = "USER")
    void testGetWineById()
    {
        Winery winery = new Winery();
        winery.setId(1L);
        winery.setName("Test");

        Type type = new Type();
        type.setId(1L);
        type.setName("Test");

        Region region = new Region();
        region.setId(1L);
        region.setName("Test");
        region.setCountry("CountryTest");

        Wine wine = new Wine();
        wine.setName("Test");
        wine.setId(1L);
        wine.setAcidity(3);
        wine.setBody(5);
        wine.setPrice(286);
        wine.setRating(4);
        wine.setNum_reviews(40);
        wine.setYear(2005);
        wine.setWinery(winery);
        wine.setRegion(region);
        wine.setType(type);

        Mockito
                .when(repository.findById(1L))
                .thenReturn(Optional.of(wine));

        webTestClient.get().uri("/api/wine/{id}", 1)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isNotEmpty()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.name").isEqualTo("Test")
                .jsonPath("$.year").isEqualTo(2005)
                .jsonPath("$.num_reviews").isEqualTo(40)
                .jsonPath("$.rating").isEqualTo(4)
                .jsonPath("$.price").isEqualTo(286)
                .jsonPath("$.body").isEqualTo(5)
                .jsonPath("$.acidity").isEqualTo(3)
                .jsonPath("$.winery").isNotEmpty()
                .jsonPath("$.region").isNotEmpty()
                .jsonPath("$.type").isNotEmpty();


        Mockito.verify(repository, times(1)).findById(1l);

    }

    @Test
    @WithMockUser(username = "ben", password = "benspassword", roles = "USER")
    void testDeleteWine()
    {
        Winery winery = new Winery();
        winery.setId(1L);
        winery.setName("Test");

        Type type = new Type();
        type.setId(1L);
        type.setName("Test");

        Region region = new Region();
        region.setId(1L);
        region.setName("Test");
        region.setCountry("CountryTest");

        Wine wine = new Wine();
        wine.setId(1L);
        wine.setAcidity(3);
        wine.setBody(5);
        wine.setPrice(286);
        wine.setRating(4);
        wine.setNum_reviews(40);
        wine.setYear(2005);
        wine.setWinery(winery);
        wine.setRegion(region);
        wine.setType(type);

        Mockito
                .when(repository.findById(1L))
                .thenReturn(Optional.of(wine));

        webTestClient.get().uri("/api/wine/{id}", 1)
                .exchange()
                .expectStatus().isOk();

    }


    @Test
    @WithMockUser(username = "ben", password = "benspassword", roles = "USER")
    void testGetWineNotExist()
    {

        webTestClient.get().uri("/api/wine/{id}", 0)
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().valueEquals("Content-Type", "text/plain;charset=UTF-8");
    }
}