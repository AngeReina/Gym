package co.edu.unbosque.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;

public record PagoDTO(
        @NotNull(message = "La factura es obligatoria") Long idFactura,
        @Positive(message = "El monto debe ser mayor a cero") Double totalRecibo,
        @NotNull(message = "Debe incluir métodos de pago")
        @Size(min = 1, message = "Debe haber al menos un método de pago")
        List<DetalleMetodoDTO> metodos
) {}