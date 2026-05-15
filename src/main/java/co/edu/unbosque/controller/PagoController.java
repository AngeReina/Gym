package co.edu.unbosque.controller;

import co.edu.unbosque.model.request.PagoDTO;
import co.edu.unbosque.service.PagoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pagos")
@CrossOrigin("*")
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @PostMapping
    public ResponseEntity<?> registrarPago(@RequestBody @Valid PagoDTO pagoDTO) {
        pagoService.registrarPagoMixto(pagoDTO);
        return ResponseEntity.ok("Pago registrado y factura actualizada con éxito");
    }
}