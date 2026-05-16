package co.edu.unbosque.model.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record FacturaDTO(
        @NotNull
        Long idSuscripcion,

        LocalDate fechaEmision,

        @NotNull
        Double total
) {}