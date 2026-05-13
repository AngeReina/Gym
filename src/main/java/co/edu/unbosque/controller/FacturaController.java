package co.edu.unbosque.controller;

import co.edu.unbosque.entity.Factura;
import co.edu.unbosque.service.FacturaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    private final FacturaService facturaService;

    public FacturaController(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    @GetMapping
    public ResponseEntity<List<Factura>> listarHistorial() {
        return ResponseEntity.ok(facturaService.obtenerHistorial());
    }
}