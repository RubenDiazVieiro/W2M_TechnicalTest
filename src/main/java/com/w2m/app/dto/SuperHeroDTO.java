package com.w2m.app.dto;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SuperHeroDTO implements Serializable {

    @Nonnull
    private Long id;
    @Nonnull
    private String name;

}
