package co.edu.unbosque.controller;

import co.edu.unbosque.entity.Plan;
import co.edu.unbosque.model.request.PlanDTO;
import co.edu.unbosque.service.PlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/planes")
@CrossOrigin("*")
public class PlanController {

    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @PostMapping
    public ResponseEntity<String> crear(@RequestBody PlanDTO dto) {
        planService.crearPlan(dto);
        return ResponseEntity.ok("Plan creado correctamente");
    }

    @GetMapping
    public ResponseEntity<List<Plan>> listar() {
        return ResponseEntity.ok(planService.listarPlanes());
    }

    @GetMapping("/por-precio")
    public ResponseEntity<List<Plan>> ordenarPorPrecio() {
        return ResponseEntity.ok(planService.ordenarPorPrecio());
    }

    @GetMapping("/largos")
    public ResponseEntity<List<Plan>> planesLargos(@RequestParam Integer dias) {
        return ResponseEntity.ok(planService.planesLargos(dias));
    }

    @GetMapping("/mas-comprados")
    public ResponseEntity<List<Object[]>> masComprados() {
        return ResponseEntity.ok(planService.planesMasComprados());
    }

    @GetMapping("/ingresos")
    public ResponseEntity<List<Object[]>> ingresos() {
        return ResponseEntity.ok(planService.ingresosPorPlan());
    }

    @GetMapping("/activos")
    public ResponseEntity<List<Object[]>> activos() {
        return ResponseEntity.ok(planService.planesActivos());
    }
}