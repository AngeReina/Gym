package co.edu.unbosque.controller;

import co.edu.unbosque.entity.Suscripcion;
import co.edu.unbosque.model.request.SuscripcionDTO;
import co.edu.unbosque.service.SuscripcionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suscripciones")
@CrossOrigin("*")
public class SuscripcionController {

    private final SuscripcionService suscripcionService;

    public SuscripcionController(SuscripcionService suscripcionService) {
        this.suscripcionService = suscripcionService;
    }

    @PostMapping
    public ResponseEntity<?> crearSuscripcion(@RequestBody @Valid SuscripcionDTO dto) {
        suscripcionService.crearSuscripcion(dto);
        return ResponseEntity.ok("Suscripción creada exitosamente");
    }
    @GetMapping
    public ResponseEntity<List<Suscripcion>> listar() {
        return ResponseEntity.ok(suscripcionService.listarSuscripciones());
    }
}