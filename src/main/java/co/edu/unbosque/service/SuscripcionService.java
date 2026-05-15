package co.edu.unbosque.service;

import co.edu.unbosque.entity.Cliente;
import co.edu.unbosque.entity.Plan;
import co.edu.unbosque.entity.Suscripcion;
import co.edu.unbosque.model.request.SuscripcionDTO;
import co.edu.unbosque.model.request.SuscripcionDTOResponse;
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

        LocalDate inicio = dto.fechaInicio() != null ? dto.fechaInicio() : LocalDate.now();

        int dias = plan.getDuracionDias() != null ? plan.getDuracionDias() : 30;

        Suscripcion s = new Suscripcion();
        s.setCliente(cliente);
        s.setPlan(plan);
        s.setFechaInicio(inicio);
        s.setFechaFin(inicio.plusDays(dias));
        s.setEstado("ACTIVO");

        suscripcionRepository.save(s);
    }

    public List<SuscripcionDTOResponse> listarSuscripciones() {

        return suscripcionRepository.listarCompleto()
                .stream()
                .map(s -> new SuscripcionDTOResponse(
                        ((Number) s[0]).longValue(),
                        (String) s[4] + " " + (String) s[5],
                        (String) s[6],
                        ((java.sql.Date) s[1]).toLocalDate(),
                        ((java.sql.Date) s[2]).toLocalDate(),
                        (String) s[3]
                ))
                .toList();
    }
}