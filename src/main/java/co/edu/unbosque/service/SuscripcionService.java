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
                .orElseThrow(() -> new RuntimeException("Cliente no existe"));
        Plan plan = planRepository.findById(dto.idPlan())
                .orElseThrow(() -> new RuntimeException("Plan no existe"));

        Suscripcion s = new Suscripcion();
        s.setCliente(cliente);
        s.setPlan(plan);
        s.setFechaInicio(dto.fechaInicio() != null ? dto.fechaInicio() : LocalDate.now());
        s.setFechaFin(s.getFechaInicio().plusMonths(1));
        s.setCosto(plan.getCosto());
        s.setEstado("ACTIVA");
        s.setBeneficios(dto.beneficios());

        suscripcionRepository.save(s);
    }
}
