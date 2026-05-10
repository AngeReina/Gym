package co.edu.unbosque.service;

import co.edu.unbosque.entity.Instructor;
import co.edu.unbosque.model.request.InstructorDTO;
import co.edu.unbosque.repository.InstructorRepository;
import org.springframework.stereotype.Service;

@Service
public class InstructorService {
    private final InstructorRepository instructorRepository;

    public InstructorService(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    public void registrar(InstructorDTO dto) {
        Instructor i = new Instructor();
        i.setPrimerNombre(dto.primerNombre());
        i.setPrimerApellido(dto.primerApellido());
        i.setNumeroDocumento(dto.numeroDocumento());
        i.setEspecialidad(dto.especialidad());
        i.setTelefono(dto.telefono());
        i.setEmail(dto.email());
        instructorRepository.save(i);
    }
}