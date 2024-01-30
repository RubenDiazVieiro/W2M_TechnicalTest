package com.w2m.app.controller;

import com.w2m.app.anotation.TrackExecutionTime;
import com.w2m.app.dto.SuperHeroDTO;
import com.w2m.app.exception.ResourceAlreadyExistsException;
import com.w2m.app.exception.ResourceNotFoundException;
import com.w2m.app.model.SuperHero;
import com.w2m.app.service.ISuperHeroService;
import com.w2m.app.service.SuperHeroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/superheroes")
@Validated
@Slf4j
@CrossOrigin
public class SuperHeroController {

    private final ISuperHeroService superHeroService;
    private final ModelMapper mapper;

    @Autowired
    public SuperHeroController(SuperHeroService superHeroService) {
        this.superHeroService = superHeroService;
        this.mapper = new ModelMapper();
    }

    @TrackExecutionTime
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Retrieve the list of superheroes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<List<SuperHeroDTO>> getSuperHeroes(@RequestParam(name = "nameFilter", required = false) String nameFilter) {
        try {
            return ResponseEntity.ok(superHeroService.getAll(nameFilter)
                    .stream()
                    .map(this::modelToDto)
                    .collect(Collectors.toList()));
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Retrieve information about a specific supehero")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Superhero not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<SuperHeroDTO> getSuperHero(@PathVariable("id") Long id) {
        try {
            return superHeroService.find(id)
                    .map(this::modelToDto)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @TrackExecutionTime
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Create a new superhero")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "409", description = "Superhero already exists"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<SuperHeroDTO> createSuperHero(@RequestBody @Validated SuperHeroDTO superHeroDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(modelToDto(superHeroService.save(dtoToModel(superHeroDto))));
        } catch (ResourceAlreadyExistsException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Update information about a specific superhero")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Superhero updated successfully"),
            @ApiResponse(responseCode = "404", description = "Superhero not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<SuperHeroDTO> update(@PathVariable("id") Long id, @RequestBody @Validated SuperHeroDTO superHeroDTO){
        try {
            return ResponseEntity.ok(modelToDto(superHeroService.update(id, dtoToModel(superHeroDTO))));
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping(path = "{id}")
    @Operation(description = "Delete a specific superhero")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Superhero deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Superhero not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<SuperHeroDTO> delete(@PathVariable("id") Long id) {
        try {
            superHeroService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    private SuperHeroDTO modelToDto(SuperHero superHero) {
        return mapper.map(superHero, SuperHeroDTO.class);
    }

    private SuperHero dtoToModel(SuperHeroDTO superHero) {
        return mapper.map(superHero, SuperHero.class);
    }

}
