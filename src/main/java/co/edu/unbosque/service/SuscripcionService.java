package co.edu.unbosque.service;

import co.edu.unbosque.entity.Cliente;
import co.edu.unbosque.entity.Plan;
import co.edu.unbosque.entity.Suscripcion;
import co.edu.unbosque.model.request.SuscripcionDTO;
import co.edu.unbosque.repository.ClienteRepository;
import co.edu.unbosque.repository.PlanRepository;
import co.edu.unbosque.repository.SuscripcionRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class SuscripcionService {

    private final SuscripcionRepository suscripcionRepository;
    private final ClienteRepository clienteRepository;
    private final PlanRepository planRepository;

    public SuscripcionService(SuscripcionRepository suscripcionRepository,
                              ClienteRepository clienteRepository,
                              PlanRepository planRepository) {
        this.suscripcionRepository = suscripcionRepository;
        this.clienteRepository = clienteRepository;
        this.planRepository = planRepository;
    }

    public void crearSuscripcion(SuscripcionDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.idCliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Plan plan = planRepository.findById(dto.idPlan())
                .orElseThrow(() -> new RuntimeException("Plan no encontrado"));

        Suscripcion s = new Suscripcion();
        s.setCliente(cliente);
        s.setPlan(plan);
        s.setFechaInicio(dto.fechaInicio());
        try {
            long dias = Long.parseLong(plan.getDuracion());
            s.setFechaFin(dto.fechaInicio().plusDays(dias));
        } catch (NumberFormatException e) {
            // Valor por defecto si la conversión falla (ej. 30 días)
            s.setFechaFin(dto.fechaInicio().plusDays(30));
        }

        s.setEstado("ACTIVO");

        suscripcionRepository.save(s);
    }

    public List<Suscripcion> listarSuscripciones() {
        return suscripcionRepository.findAll();
    }
}