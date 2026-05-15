package co.edu.unbosque.service;

import co.edu.unbosque.entity.Instructor;
import co.edu.unbosque.model.request.InstructorDTO;
import co.edu.unbosque.repository.InstructorRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InstructorService {
    private final InstructorRepository instructorRepository;

    public InstructorService(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    public void registrar(InstructorDTO dto) {
        Instructor i = new Instructor();

        i.setPrimerNombre(dto.primerNombre());
        i.setSegundoNombre(dto.segundoNombre());
        i.setPrimerApellido(dto.primerApellido());
        i.setSegundoApellido(dto.segundoApellido());
        i.setEspecialidad(dto.especialidad());
        i.setTelefono(dto.telefono());

        i.setNumeroDocumento(dto.numeroDocumento());
        i.setTipoDocumento(dto.tipoDocumento());
        i.setEmail(dto.email());

        instructorRepository.save(i);
    }

    public List<Instructor> listarTodos() {
        return instructorRepository.findAll();
    }

    public List<Instructor> buscarPorEspecialidad(String especialidad) {
        return instructorRepository.porEspecialidad(especialidad);
    }}