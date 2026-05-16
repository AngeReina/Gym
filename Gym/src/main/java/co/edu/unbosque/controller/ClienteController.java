package co.edu.unbosque.controller;

import co.edu.unbosque.model.request.ClienteDTO;
import co.edu.unbosque.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin("*")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody @Valid ClienteDTO clienteDTO) {
        clienteService.registrar(clienteDTO);
        return ResponseEntity.ok("Cliente registrado exitosamente");
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(clienteService.listarTodos());
    }

    @GetMapping("/ranking")
    public ResponseEntity<?> obtenerRanking() {
        return ResponseEntity.ok(clienteService.obtenerRanking());
    }
}