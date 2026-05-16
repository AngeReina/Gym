package co.edu.unbosque.service;

import co.edu.unbosque.entity.Clase;
import co.edu.unbosque.model.request.ClaseDTO;
import co.edu.unbosque.repository.ClaseRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClaseService {

    private final ClaseRepository claseRepository;

    public ClaseService(ClaseRepository claseRepository) {
        this.claseRepository = claseRepository;
    }

    public void crearClase(ClaseDTO dto) {
        Clase clase = new Clase();
        clase.setNombreClase(dto.nombreClase());
        clase.setDescripcion(dto.descripcion());

        claseRepository.save(clase);
    }

    public List<Clase> listarClases() {
        return claseRepository.listarOrdenadas();
    }

    public Long obtenerTotalClases() {
        return claseRepository.contarClases();
    }
}