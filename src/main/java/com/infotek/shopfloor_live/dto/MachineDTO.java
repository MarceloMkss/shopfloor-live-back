package com.infotek.shopfloor_live.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MachineDTO(
        Long id,
        @NotBlank @Size(min = 3, max = 100) String name,
        @NotBlank @Size(min = 2, max = 100) String vendor,
        @NotBlank @Size(min = 2, max = 100) String model,
        @NotBlank String status // ACTIVE | INACTIVE
) {}
