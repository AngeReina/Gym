package co.edu.unbosque.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ClienteDTO(
        @NotBlank(message = "El primer nombre es obligatorio") String primerNombre,
        String segundoNombre,
        @NotBlank(message = "El primer apellido es obligatorio") String primerApellido,
        String segundoApellido,
        @NotBlank(message = "El documento es obligatorio") String numeroDocumento,
        @NotBlank(message = "El tipo de documento es obligatorio") String tipoDocumento,
        String telefono,
        @Email(message = "El formato del correo es inválido") String email
) {}
