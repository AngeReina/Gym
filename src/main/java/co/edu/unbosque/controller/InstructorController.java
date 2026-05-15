package co.edu.unbosque.controller;

import co.edu.unbosque.entity.Instructor;
import co.edu.unbosque.model.request.InstructorDTO;
import co.edu.unbosque.service.InstructorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instructores")
@CrossOrigin("*")
public class InstructorController {

    private final InstructorService instructorService;

    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @PostMapping
    public ResponseEntity<String> registrar(@Valid @RequestBody InstructorDTO dto) {
        instructorService.registrar(dto);
        return ResponseEntity.ok("Instructor registrado exitosamente");
    }

    @GetMapping
    public ResponseEntity<List<Instructor>> listar() {
        return ResponseEntity.ok(instructorService.listarTodos());
    }
}