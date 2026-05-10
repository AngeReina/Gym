package co.edu.unbosque.service;

import co.edu.unbosque.entity.Sesion;
import co.edu.unbosque.repository.SesionRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SesionService {
    private final SesionRepository sesionRepository;

    public SesionService(SesionRepository sesionRepository) {
        this.sesionRepository = sesionRepository;
    }

    public void crearSesion(Sesion sesion) {
        sesion.setCuposDisponibles(sesion.getCupoMax());
        sesionRepository.save(sesion);
    }

    public List<Sesion> listarSesiones() {
        return sesionRepository.findAll();
    }
}