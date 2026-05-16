package co.edu.unbosque.service;

import co.edu.unbosque.entity.Clase;
import co.edu.unbosque.entity.Instructor;
import co.edu.unbosque.entity.Sesion;
import co.edu.unbosque.model.request.SesionDTO;
import co.edu.unbosque.model.request.SesionDTOResponse;
import co.edu.unbosque.repository.ClaseRepository;
import co.edu.unbosque.repository.InstructorRepository;
import co.edu.unbosque.repository.SesionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

        if (dto.horaFin().isBefore(dto.horaInicio())
                || dto.horaFin().equals(dto.horaInicio())) {

            throw new RuntimeException(
                    "La hora fin debe ser mayor a la hora inicio"
            );
        }

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

    public List<SesionDTOResponse> listarSesiones() {

        return sesionRepository.listarCompleto()
                .stream()
                .map(s -> new SesionDTOResponse(
                        ((Number) s[0]).longValue(),
                        (String) s[1],
                        (String) s[2],
                        ((java.sql.Date) s[3]).toLocalDate(),
                        ((java.sql.Time) s[4]).toLocalTime(),
                        ((java.sql.Time) s[5]).toLocalTime(),
                        ((Number) s[6]).intValue(),
                        ((Number) s[7]).intValue()
                ))
                .toList();
    }
    public List<Object[]> porFecha(LocalDate fecha) {
        return sesionRepository.porFecha(fecha);
    }

    public List<Object[]> porInstructor(Long idInstructor) {
        return sesionRepository.porInstructor(idInstructor);
    }

    public List<Object[]> conCupos() {
        return sesionRepository.conCuposDisponibles();
    }

    public List<Object[]> masAsistidas() {
        return sesionRepository.sesionesMasAsistidas();
    }
}