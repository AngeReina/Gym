package co.edu.unbosque.model.request;

public record FacturaDTOResponse(
        Long id,
        Double total,
        String estado,
        Long idSuscripcion
) {}
