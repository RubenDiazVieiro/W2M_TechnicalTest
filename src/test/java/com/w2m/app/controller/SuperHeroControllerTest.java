package com.w2m.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.w2m.app.model.SuperHero;
import com.w2m.app.service.SuperHeroService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = SuperHeroController.class)
public class SuperHeroControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private SuperHeroService superHeroService;

    @Test
    public void shouldSaveSuperHero() throws Exception {
        SuperHero superHero = SuperHero.builder()
                .id(1L)
                .name("Batman")
                .build();

        when(superHeroService.save(superHero)).thenReturn(superHero);

        mvc.perform(post("/superheroes").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(superHero)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturnSuperHero() throws Exception {
        long id = 1L;
        SuperHero superHero = SuperHero.builder()
                .id(1L)
                .name("Batman")
                .build();

        when(superHeroService.find(id)).thenReturn(Optional.of(superHero));
        mvc.perform(get("/superheroes/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(superHero.getName()));
    }

    @Test
    void shouldReturnNotFoundSuperHero() throws Exception {
        long id = 1L;

        when(superHeroService.find(id)).thenReturn(Optional.empty());
        mvc.perform(get("/superheroes/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnListOfSuperHeroes() throws Exception {
        List<SuperHero> superHeroes = new ArrayList<>();
        superHeroes.add(SuperHero.builder().id(1L).name("Batman").build());
        superHeroes.add(SuperHero.builder().id(2L).name("Spiderman").build());
        superHeroes.add(SuperHero.builder().id(3L).name("Robin").build());

        when(superHeroService.getAll(null)).thenReturn(superHeroes);
        mvc.perform(get("/superheroes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(superHeroes.size()));
    }

}
