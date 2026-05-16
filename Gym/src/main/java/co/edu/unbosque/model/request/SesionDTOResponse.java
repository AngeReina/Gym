package co.edu.unbosque.model.request;

import java.time.LocalDate;
import java.time.LocalTime;

public record SesionDTOResponse(
        Long idSesion,
        String clase,
        String instructor,
        LocalDate fecha,
        LocalTime horaInicio,
        LocalTime horaFin,
        Integer cupoMax,
        Integer cuposDisponibles
) {}