package co.edu.unbosque.controller;

import co.edu.unbosque.entity.Sesion;
import co.edu.unbosque.model.request.SesionDTO;
import co.edu.unbosque.model.request.SesionDTOResponse;
import co.edu.unbosque.service.SesionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sesiones")
@CrossOrigin("*")
public class SesionController {

    private final SesionService sesionService;

    public SesionController(SesionService sesionService) {
        this.sesionService = sesionService;
    }

    @PostMapping
    public ResponseEntity<String> programar(@Valid @RequestBody SesionDTO dto) {
        sesionService.crearSesion(dto);
        return ResponseEntity.ok("Sesión programada en el horario");
    }

    @GetMapping
    public ResponseEntity<List<SesionDTOResponse>> listar() {
        return ResponseEntity.ok(sesionService.listarSesiones());
    }
    @GetMapping("/por-fecha")
    public ResponseEntity<List<Object[]>> porFecha(@RequestParam String fecha) {
        return ResponseEntity.ok(
                sesionService.porFecha(java.time.LocalDate.parse(fecha))
        );
    }

    @GetMapping("/instructor/{id}")
    public ResponseEntity<List<Object[]>> porInstructor(@PathVariable Long id) {
        return ResponseEntity.ok(
                sesionService.porInstructor(id)
        );
    }

    @GetMapping("/con-cupos")
    public ResponseEntity<List<Object[]>> conCupos() {
        return ResponseEntity.ok(
                sesionService.conCupos()
        );
    }

    @GetMapping("/mas-asistidas")
    public ResponseEntity<List<Object[]>> masAsistidas() {
        return ResponseEntity.ok(
                sesionService.masAsistidas()
        );
    }
}