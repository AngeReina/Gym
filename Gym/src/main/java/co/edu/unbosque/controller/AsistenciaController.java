package co.edu.unbosque.controller;

import co.edu.unbosque.model.request.AsistenciaDTO;
import co.edu.unbosque.model.request.AsistenciaDTOResponse;
import co.edu.unbosque.service.AsistenciaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asistencias")
@CrossOrigin("*")
public class AsistenciaController {

    private final AsistenciaService asistenciaService;

    public AsistenciaController(AsistenciaService asistenciaService) {
        this.asistenciaService = asistenciaService;
    }

    @PostMapping("/marcar")
    public ResponseEntity<?> marcarAsistencia(@RequestBody @Valid AsistenciaDTO dto) {
        try {
            asistenciaService.registrarAsistencia(dto);
            return ResponseEntity.ok("Asistencia registrada correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<AsistenciaDTOResponse>> listarAsistencias() {
        return ResponseEntity.ok(asistenciaService.listarTodas());
    }

    @GetMapping("/sesion/{id}")
    public ResponseEntity<List<AsistenciaDTOResponse>> filtrarPorSesion(@PathVariable Long id) {
        return ResponseEntity.ok(asistenciaService.listarPorSesion(id));
    }
}