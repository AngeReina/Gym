package co.edu.unbosque.model.request;

import jakarta.validation.constraints.NotBlank;

public record ClienteDTO(
        @NotBlank String primerNombre,
        String segundoNombre,
        @NotBlank String primerApellido,
        String segundoApellido,
        @NotBlank String numeroDocumento,
        @NotBlank String tipoDocumento,
        String telefono,
        String email
) {}