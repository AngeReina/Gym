package co.edu.unbosque.model.request;

import jakarta.validation.constraints.NotNull;

public record AsistenciaDTO(
        @NotNull(message = "El código del cliente es obligatorio")
        Long idCliente,

        @NotNull(message = "El identificador de la sesión es obligatorio")
        Long idSesion,

        String estadoAsistencia
) {}