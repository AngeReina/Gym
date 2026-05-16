package co.edu.unbosque.model.request;

public record DetalleMetodoDTO(
        String tipoMetodo,
        Double monto,
        String franquicia,
        String tipoTarjeta,
        String bancoOrigen,
        String numTransaccion
) {}
