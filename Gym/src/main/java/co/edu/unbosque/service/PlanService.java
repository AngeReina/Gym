package co.edu.unbosque.service;

import co.edu.unbosque.entity.Plan;
import co.edu.unbosque.model.request.PlanDTO;
import co.edu.unbosque.repository.PlanRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PlanService {

    private final PlanRepository planRepository;

    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    public void crearPlan(PlanDTO dto) {
        Plan plan = new Plan();
        plan.setNombrePlan(dto.nombrePlan());
        plan.setCosto(dto.precio());
        plan.setDuracionDias(dto.duracionDias());

        planRepository.save(plan);
    }

    public List<Plan> listarPlanes() {
        return planRepository.listarTodos();
    }
    public List<Plan> ordenarPorPrecio() {
        return planRepository.ordenarPorPrecio();
    }

    public List<Plan> planesLargos(Integer dias) {
        return planRepository.planesLargos(dias);
    }

    public List<Object[]> planesMasComprados() {
        return planRepository.planesMasComprados();
    }

    public List<Object[]> ingresosPorPlan() {
        return planRepository.ingresosPorPlan();
    }

    public List<Object[]> planesActivos() {
        return planRepository.planesActivos();
    }
}