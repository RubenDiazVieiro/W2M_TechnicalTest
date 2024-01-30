package com.w2m.app.service;

import com.w2m.app.config.CacheConfig;
import com.w2m.app.exception.ResourceAlreadyExistsException;
import com.w2m.app.exception.ResourceNotFoundException;
import com.w2m.app.model.SuperHero;
import com.w2m.app.repository.SuperHeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SuperHeroService implements ISuperHeroService{

    private final SuperHeroRepository superHeroRepository;

    @Autowired
    public SuperHeroService(SuperHeroRepository superHeroRepository) {
        this.superHeroRepository = superHeroRepository;
    }

    @Cacheable(value = CacheConfig.SUPERHEROES_CACHE)
    public List<SuperHero> getAll(String nameFilter) {
        return (nameFilter != null && !nameFilter.isBlank()) ?
                superHeroRepository.findByNameContainingIgnoreCase(nameFilter) :
                superHeroRepository.findAll();
    }

    @Cacheable(value = CacheConfig.SUPERHERO_CACHE, key = "#id")
    public Optional<SuperHero> find(Long id) {
        return superHeroRepository.findById(id);
    }

    @Caching(evict = {
            @CacheEvict(value=CacheConfig.SUPERHEROES_CACHE, allEntries=true),
            @CacheEvict(value=CacheConfig.SUPERHERO_CACHE, key = "#superHero.id")})
    public SuperHero save(SuperHero superHero) throws ResourceAlreadyExistsException {
        if (!superHeroRepository.existsById(superHero.getId())) {
            return superHeroRepository.save(superHero);
        }
        throw new ResourceAlreadyExistsException("Superhero with id "+superHero.getId()+" already exists");
    }

    @Caching(evict = {
            @CacheEvict(value=CacheConfig.SUPERHEROES_CACHE, allEntries=true),
            @CacheEvict(value=CacheConfig.SUPERHERO_CACHE, key = "#id")})
    public SuperHero update(Long id, SuperHero superHero) throws ResourceNotFoundException {
        if (superHeroRepository.existsById(id) && id.equals(superHero.getId())) {
            return superHeroRepository.save(superHero);
        }
        throw new ResourceNotFoundException("Superhero with id "+ id +" not found");
    }

    @Caching(evict = {
            @CacheEvict(value=CacheConfig.SUPERHEROES_CACHE, allEntries=true),
            @CacheEvict(value=CacheConfig.SUPERHERO_CACHE, key = "#id")})
    public void delete(Long id) throws ResourceNotFoundException {
        superHeroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Superhero with id "+ id +" not found"));
        superHeroRepository.deleteById(id);
    }

}
