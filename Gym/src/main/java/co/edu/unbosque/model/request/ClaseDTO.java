package co.edu.unbosque.model.request;

import jakarta.validation.constraints.NotBlank;

public record ClaseDTO(
        @NotBlank
        String nombreClase,

        String descripcion
) {}