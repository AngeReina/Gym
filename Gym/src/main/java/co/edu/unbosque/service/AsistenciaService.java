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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AsistenciaService {

    private final AsistenciaRepository asistenciaRepository;
    private final ClienteRepository clienteRepository;
    private final SesionRepository sesionRepository;

    public AsistenciaService(AsistenciaRepository asistenciaRepository,
                             ClienteRepository clienteRepository,
                             SesionRepository sesionRepository) {
        this.asistenciaRepository = asistenciaRepository;
        this.clienteRepository = clienteRepository;
        this.sesionRepository = sesionRepository;
    }

    public void registrarAsistencia(AsistenciaDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.idCliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Sesion sesion = sesionRepository.findById(dto.idSesion())
                .orElseThrow(() -> new RuntimeException("Sesión no encontrada"));

        Asistencia asistencia = new Asistencia();
        asistencia.setCliente(cliente);
        asistencia.setSesion(sesion);
        asistencia.setEstadoAsistencia(EstadoAsistencia.PRESENTE);

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