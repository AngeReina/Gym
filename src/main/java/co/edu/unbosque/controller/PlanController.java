package co.edu.unbosque.controller;

import co.edu.unbosque.entity.Plan;
import co.edu.unbosque.model.request.PlanDTO;
import co.edu.unbosque.service.PlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/planes")
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
}