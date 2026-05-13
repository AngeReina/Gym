package co.edu.unbosque.controller;

import co.edu.unbosque.entity.Sesion;
import co.edu.unbosque.model.request.SesionDTO;
import co.edu.unbosque.service.SesionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sesiones")
public class SesionController {

    private final SesionService sesionService;

    public SesionController(SesionService sesionService) {
        this.sesionService = sesionService;
    }

    @PostMapping
    public ResponseEntity<String> programar(@RequestBody SesionDTO dto) {
        sesionService.crearSesion(dto);
        return ResponseEntity.ok("Sesión programada en el horario");
    }

    @GetMapping
    public ResponseEntity<List<Sesion>> listar() {
        return ResponseEntity.ok(sesionService.listarSesiones());
    }
}