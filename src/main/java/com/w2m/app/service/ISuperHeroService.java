package com.w2m.app.service;

import com.w2m.app.exception.ResourceAlreadyExistsException;
import com.w2m.app.exception.ResourceNotFoundException;
import com.w2m.app.model.SuperHero;

import java.util.List;
import java.util.Optional;

public interface ISuperHeroService {

    List<SuperHero> getAll(String nameFilter);

    Optional<SuperHero> find(Long id);

    SuperHero save(SuperHero superHero) throws ResourceAlreadyExistsException;

    SuperHero update(Long id, SuperHero superHero) throws ResourceNotFoundException;

    void delete(Long id) throws ResourceNotFoundException;

}
