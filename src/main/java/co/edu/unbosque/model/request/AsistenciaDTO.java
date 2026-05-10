package co.edu.unbosque.model.request;

import jakarta.validation.constraints.NotNull;

public record AsistenciaDTO(
        @NotNull Long idCliente,
        @NotNull Long idSesion
) {}
