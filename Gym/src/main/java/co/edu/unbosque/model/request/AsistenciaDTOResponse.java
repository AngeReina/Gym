package co.edu.unbosque.model.request;

import java.time.LocalDateTime;

public record AsistenciaDTOResponse(
        Long idAsistencia,
        String clienteNombre,
        Long idSesion,
        LocalDateTime fechaRegistro,
        String estadoAsistencia
) {}