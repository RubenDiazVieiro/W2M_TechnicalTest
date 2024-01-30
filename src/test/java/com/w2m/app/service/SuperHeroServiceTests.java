package com.w2m.app.service;

import com.w2m.app.model.SuperHero;
import com.w2m.app.repository.SuperHeroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
public class SuperHeroServiceTests {

    @Mock
    private SuperHeroRepository superHeroRepository;

    @InjectMocks
    private SuperHeroService superHeroService;

    private SuperHero superHero;

    @BeforeEach
    void setup() {
        superHero = SuperHero.builder()
                .id(1L)
                .name("Batman")
                .build();
    }

    @Test
    void shouldSaveSuperHero() {
        given(superHeroRepository.save(superHero))
                .willReturn(superHero);

        SuperHero savedSuperHero = superHeroService.save(superHero);

        assertThat(savedSuperHero).isNotNull();
    }

    @Test
    void shouldListSuperHeroes() {
        SuperHero superHero1 = SuperHero.builder().id(2L).name("Superman").build();

        given(superHeroRepository.findAll())
                .willReturn(List.of(superHero, superHero1));

        List<SuperHero> superHeroes = superHeroService.getAll(null);

        assertThat(superHeroes).isNotNull();
        assertThat(superHeroes).size().isEqualTo(2);
    }

}
