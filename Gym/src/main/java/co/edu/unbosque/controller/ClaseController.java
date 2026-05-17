package co.edu.unbosque.controller;

import co.edu.unbosque.entity.Clase;
import co.edu.unbosque.model.request.ClaseDTO;
import co.edu.unbosque.service.ClaseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/clases")
@CrossOrigin("*")
public class ClaseController {

    private final ClaseService claseService;

    public ClaseController(ClaseService claseService) {
        this.claseService = claseService;
    }

    @PostMapping
    public ResponseEntity<String> crear(@RequestBody @Valid ClaseDTO dto) {
        claseService.crearClase(dto);
        return ResponseEntity.ok("Clase creada correctamente");
    }

    @GetMapping
    public ResponseEntity<List<Clase>> listar() {
        return ResponseEntity.ok(claseService.listarClases());
    }

    @GetMapping("/total")
    public ResponseEntity<Long> total() {
        return ResponseEntity.ok(claseService.obtenerTotalClases());
    }
}