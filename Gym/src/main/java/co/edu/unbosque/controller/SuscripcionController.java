package co.edu.unbosque.controller;

import co.edu.unbosque.model.request.SuscripcionDTO;
import co.edu.unbosque.model.request.SuscripcionDTOResponse;
import co.edu.unbosque.service.SuscripcionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/suscripciones")
@CrossOrigin("*")
public class SuscripcionController {

    private final SuscripcionService service;

    public SuscripcionController(SuscripcionService service) {
        this.service = service;
    }

    @GetMapping
    public List<SuscripcionDTOResponse> listar() {
        return service.listarSuscripciones(); // usa el servicio con DTOs
    }

    @GetMapping("/cliente/{id}")
    public List<SuscripcionDTOResponse> porCliente(@PathVariable Long id) {
        return service.porCliente(id);
    }

    @GetMapping("/estado/{estado}")
    public List<SuscripcionDTOResponse> porEstado(@PathVariable String estado) {
        return service.porEstado(estado);
    }

    @PostMapping
    public ResponseEntity<Void> crear(@RequestBody @Valid SuscripcionDTO dto) {
        service.crearSuscripcion(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}