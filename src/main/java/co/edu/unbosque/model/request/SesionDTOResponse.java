package co.edu.unbosque.model.request;

import java.time.LocalDate;

public record SesionDTOResponse(
        Long idSesion,
        String clase,
        String instructor,
        LocalDate fecha,
        Integer cupoMax,
        Integer cuposDisponibles
) {}
