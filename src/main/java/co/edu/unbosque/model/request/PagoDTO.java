package co.edu.unbosque.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record PagoDTO(
        @NotNull(message = "La factura es obligatoria") Long idFactura,
        @Positive(message = "El monto debe ser mayor a cero") Double totalRecibo,
        @NotNull(message = "Debe incluir al menos un método de pago") List<DetalleMetodoDTO> metodos
) {}