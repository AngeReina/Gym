package co.edu.unbosque.controller;

import co.edu.unbosque.entity.Factura;
import co.edu.unbosque.model.request.FacturaDTO;
import co.edu.unbosque.model.request.FacturaDTOResponse;
import co.edu.unbosque.service.FacturaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facturas")
@CrossOrigin("*")
public class FacturaController {

    private final FacturaService facturaService;

    public FacturaController(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    @GetMapping
    public ResponseEntity<List<FacturaDTOResponse>> listarHistorial() {
        return ResponseEntity.ok(facturaService.obtenerHistorial());
    }
    @PostMapping
    public ResponseEntity<Void> emitirFactura(@RequestBody FacturaDTO dto) {
        facturaService.emitirFactura(dto);
        return ResponseEntity.ok().build();
    }
}