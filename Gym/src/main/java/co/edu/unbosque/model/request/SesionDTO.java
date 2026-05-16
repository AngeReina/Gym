package co.edu.unbosque.model.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

public record SesionDTO(
        @NotNull
        Long idClase,

        @NotNull
        Long idInstructor,

        @NotNull
        @FutureOrPresent
        LocalDate fecha,

        @NotNull
        LocalTime horaInicio,

        @NotNull
        LocalTime horaFin,

        @NotNull
        @Min(1)
        Integer cupoMax,

        @Min(0)
        Integer cuposDisponibles
) {}