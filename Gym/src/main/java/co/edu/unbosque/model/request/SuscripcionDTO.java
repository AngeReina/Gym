package co.edu.unbosque.model.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record SuscripcionDTO(
        @NotNull Long idCliente,
        @NotNull Long idPlan,
        @NotNull
        LocalDate fechaInicio,
        String beneficios
) {}