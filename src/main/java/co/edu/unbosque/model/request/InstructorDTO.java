package co.edu.unbosque.model.request;

import jakarta.validation.constraints.NotBlank;

public record InstructorDTO(
        @NotBlank String primerNombre,
        String segundoNombre,
        @NotBlank String primerApellido,
        @NotBlank String numeroDocumento,
        @NotBlank String especialidad,
        String telefono,
        String email
) {}
