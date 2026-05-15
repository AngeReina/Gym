package co.edu.unbosque.controller;

import co.edu.unbosque.entity.Clase;
import co.edu.unbosque.model.request.ClaseDTO;
import co.edu.unbosque.service.ClaseService;
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
    public ResponseEntity<String> crear(@RequestBody ClaseDTO dto) {
        claseService.crearClase(dto);
        return ResponseEntity.ok("Clase creada exitosamente");
    }

    @GetMapping
    public ResponseEntity<List<Clase>> listar() {
        return ResponseEntity.ok(claseService.listarClases());
    }
}