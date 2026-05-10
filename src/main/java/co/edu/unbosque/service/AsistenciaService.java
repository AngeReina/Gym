package co.edu.unbosque.service;

import co.edu.unbosque.entity.Asistencia;
import co.edu.unbosque.entity.Cliente;
import co.edu.unbosque.entity.Sesion;
import co.edu.unbosque.entity.enums.EstadoAsistencia;
import co.edu.unbosque.model.request.AsistenciaDTO;
import co.edu.unbosque.repository.AsistenciaRepository;
import co.edu.unbosque.repository.ClienteRepository;
import co.edu.unbosque.repository.SesionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AsistenciaService {

    private final AsistenciaRepository asistenciaRepository;
    private final SesionRepository sesionRepository;
    private final ClienteRepository clienteRepository;

    public AsistenciaService(AsistenciaRepository asistenciaRepository,
                             SesionRepository sesionRepository,
                             ClienteRepository clienteRepository) {
        this.asistenciaRepository = asistenciaRepository;
        this.sesionRepository = sesionRepository;
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    public void registrarAsistencia(AsistenciaDTO dto) {
        Sesion sesion = sesionRepository.findById(dto.idSesion())
                .orElseThrow(() -> new RuntimeException("Sesión no encontrada"));

        if (sesion.getCuposDisponibles() <= 0) {
            throw new RuntimeException("No hay cupos disponibles");
        }

        Cliente cliente = clienteRepository.findById(dto.idCliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Asistencia a = new Asistencia();
        a.setEstadoAsistencia(EstadoAsistencia.PRESENTE);
        a.setCliente(cliente);
        a.setSesion(sesion);

        asistenciaRepository.save(a);

        sesion.setCuposDisponibles(sesion.getCuposDisponibles() - 1);
        sesionRepository.save(sesion);
    }
}
