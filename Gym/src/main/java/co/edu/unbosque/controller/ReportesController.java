package co.edu.unbosque.controller;

import co.edu.unbosque.service.ReportesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reportes")
@CrossOrigin("*")
public class ReportesController {

    private final ReportesService reportesService;

    public ReportesController(ReportesService reportesService) {
        this.reportesService = reportesService;
    }

    @GetMapping("/distribucion-metodos")
    public ResponseEntity<List<Object[]>> distribucionMetodos() {
        return ResponseEntity.ok(reportesService.distribucionMetodos());
    }

    @GetMapping("/ranking-clientes")
    public ResponseEntity<List<Object[]>> rankingClientes() {
        return ResponseEntity.ok(reportesService.rankingClientes());
    }

    @GetMapping("/total-efectivo")
    public ResponseEntity<Double> totalEfectivo() {
        return ResponseEntity.ok(reportesService.totalEfectivo());
    }

    @GetMapping("/total-tarjetas")
    public ResponseEntity<Double> totalTarjetas() {
        return ResponseEntity.ok(reportesService.totalTarjetas());
    }

    @GetMapping("/total-transferencias")
    public ResponseEntity<Double> totalTransferencias() {
        return ResponseEntity.ok(reportesService.totalTransferencias());
    }

    @GetMapping("/total-recaudado")
    public ResponseEntity<Double> totalRecaudado() {
        return ResponseEntity.ok(reportesService.totalRecaudado());
    }

    @GetMapping("/ranking-bancos")
    public ResponseEntity<List<Object[]>> rankingBancos() {
        return ResponseEntity.ok(reportesService.rankingBancos());
    }

    @GetMapping("/franquicias")
    public ResponseEntity<List<Object[]>> franquicias() {
        return ResponseEntity.ok(reportesService.franquicias());
    }

    @GetMapping("/planes-mas-usados")
    public ResponseEntity<List<Object[]>> planesMasUsados() {
        return ResponseEntity.ok(reportesService.planesMasUsados());
    }

    @GetMapping("/facturas-pagadas-count")
    public ResponseEntity<Long> facturasPagadasCount() {
        return ResponseEntity.ok(reportesService.facturasPagadasCount());
    }

    @GetMapping("/facturas-pagadas-total")
    public ResponseEntity<Double> facturasPagadasTotal() {
        return ResponseEntity.ok(reportesService.facturasPagadasTotal());
    }
}