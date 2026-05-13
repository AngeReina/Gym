package co.edu.unbosque.model.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record FacturaDTO(
        @NotNull
        Long idPago,

        LocalDateTime fechaEmision,

        @NotNull
        Double total,

        String detallesAdicionales
) {}