package co.edu.unbosque.service;

import co.edu.unbosque.entity.Clase;
import co.edu.unbosque.entity.Instructor;
import co.edu.unbosque.entity.Sesion;
import co.edu.unbosque.model.request.SesionDTO;
import co.edu.unbosque.repository.ClaseRepository;
import co.edu.unbosque.repository.InstructorRepository;
import co.edu.unbosque.repository.SesionRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SesionService {
    private final SesionRepository sesionRepository;
    private final ClaseRepository claseRepository;
    private final InstructorRepository instructorRepository;

    public SesionService(SesionRepository sesionRepository,
                         ClaseRepository claseRepository,
                         InstructorRepository instructorRepository) {
        this.sesionRepository = sesionRepository;
        this.claseRepository = claseRepository;
        this.instructorRepository = instructorRepository;
    }

    public void crearSesion(SesionDTO dto) {
        Clase clase = claseRepository.findById(dto.idClase())
                .orElseThrow(() -> new RuntimeException("Clase no encontrada"));

        Instructor instructor = instructorRepository.findById(dto.idInstructor())
                .orElseThrow(() -> new RuntimeException("Instructor no encontrado"));

        Sesion sesion = new Sesion();
        sesion.setClase(clase);
        sesion.setInstructor(instructor);
        sesion.setFecha(dto.fecha());
        sesion.setHoraInicio(dto.horaInicio());
        sesion.setHoraFin(dto.horaFin());
        sesion.setCupoMax(dto.cupoMax());
        sesion.setCuposDisponibles(dto.cupoMax());

        sesionRepository.save(sesion);
    }

    public List<Sesion> listarSesiones() {
        return sesionRepository.findAll();
    }
}