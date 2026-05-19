package co.edu.unbosque.service;

import co.edu.unbosque.entity.Asistencia;
import co.edu.unbosque.entity.Cliente;
import co.edu.unbosque.entity.Sesion;
import co.edu.unbosque.entity.enums.EstadoAsistencia;
import co.edu.unbosque.model.request.AsistenciaDTO;
import co.edu.unbosque.model.request.AsistenciaDTOResponse;
import co.edu.unbosque.repository.AsistenciaRepository;
import co.edu.unbosque.repository.ClienteRepository;
import co.edu.unbosque.repository.SesionRepository;
import co.edu.unbosque.repository.SuscripcionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AsistenciaService {

    private final AsistenciaRepository asistenciaRepository;
    private final ClienteRepository clienteRepository;
    private final SesionRepository sesionRepository;
    private final SuscripcionRepository suscripcionRepository;

    public AsistenciaService(AsistenciaRepository asistenciaRepository,
                             ClienteRepository clienteRepository,
                             SesionRepository sesionRepository,
                             SuscripcionRepository suscripcionRepository) {
        this.asistenciaRepository    = asistenciaRepository;
        this.clienteRepository       = clienteRepository;
        this.sesionRepository        = sesionRepository;
        this.suscripcionRepository   = suscripcionRepository;
    }

    public void registrarAsistencia(AsistenciaDTO dto) {

        Cliente cliente = clienteRepository.findById(dto.idCliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));


        if (suscripcionRepository.contarActivasPorCliente(dto.idCliente()) == 0) {
            throw new RuntimeException("El cliente no tiene una suscripción activa");
        }

        Sesion sesion = sesionRepository.findById(dto.idSesion())
                .orElseThrow(() -> new RuntimeException("Sesión no encontrada"));

        if (sesion.getCuposDisponibles() == null || sesion.getCuposDisponibles() <= 0) {
            throw new RuntimeException("No hay cupos disponibles en esta sesión");
        }

        sesion.setCuposDisponibles(sesion.getCuposDisponibles() - 1);
        sesionRepository.save(sesion);

        EstadoAsistencia estado = EstadoAsistencia.PRESENTE;
        if (dto.estadoAsistencia() != null && !dto.estadoAsistencia().isBlank()) {
            try {
                estado = EstadoAsistencia.valueOf(dto.estadoAsistencia().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Estado de asistencia inválido: " + dto.estadoAsistencia());
            }
        }

        Asistencia asistencia = new Asistencia();
        asistencia.setCliente(cliente);
        asistencia.setSesion(sesion);
        asistencia.setEstadoAsistencia(estado);

        asistenciaRepository.save(asistencia);
    }

    public List<AsistenciaDTOResponse> listarTodas() {
        return asistenciaRepository.listarCompleto()
                .stream()
                .map(this::mapear)
                .toList();
    }

    public List<AsistenciaDTOResponse> listarPorSesion(Long idSesion) {
        return asistenciaRepository.porSesion(idSesion)
                .stream()
                .map(this::mapear)
                .toList();
    }

    private AsistenciaDTOResponse mapear(Object[] r) {
        return new AsistenciaDTOResponse(
                ((Number) r[0]).longValue(),
                (String) r[3] + " " + (String) r[4],
                ((Number) r[5]).longValue(),
                ((java.sql.Timestamp) r[1]).toLocalDateTime(),
                (String) r[2]
        );
    }
}