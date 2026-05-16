package co.edu.unbosque.model.request;

import java.time.LocalDate;

public record SuscripcionDTOResponse(
        Long id,
        String clienteNombre,
        String planNombre,
        LocalDate inicio,
        LocalDate fin,
        String estado
) {}
