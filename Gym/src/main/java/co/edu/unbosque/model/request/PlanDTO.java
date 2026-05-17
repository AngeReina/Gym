package co.edu.unbosque.model.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record PlanDTO(
        @NotBlank
        String nombrePlan,

        @NotNull
        @DecimalMin("0.0")
        BigDecimal costo,

        @NotNull
        @Min(1)
        Integer duracionDias
) {}