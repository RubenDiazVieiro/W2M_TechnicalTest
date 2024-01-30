package com.w2m.app.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.w2m.app.model.SuperHero;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class SuperHeroRepositoryTests {

    @Autowired
    private SuperHeroRepository superHeroRepository;

    private SuperHero superHero;

    @BeforeEach
    void setup() {
        superHero = SuperHero.builder()
                .id(1L)
                .name("Batman")
                .build();
    }

    @Test
    void testSaveSuperHero() {
        SuperHero savedSuperHero = superHeroRepository.save(superHero);

        assertThat(savedSuperHero).isNotNull();
        assertThat(savedSuperHero.getId()).isGreaterThan(0);
    }

}
