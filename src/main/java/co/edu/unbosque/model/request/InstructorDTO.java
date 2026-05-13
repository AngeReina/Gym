package co.edu.unbosque.model.request;

import jakarta.validation.constraints.NotBlank;

public record InstructorDTO(
        @NotBlank
        String primerNombre,

        String segundoNombre,

        @NotBlank
        String primerApellido,

        String segundoApellido,

        @NotBlank
        String especialidad,

        String telefono
) {}