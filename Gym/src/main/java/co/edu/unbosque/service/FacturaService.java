package co.edu.unbosque.service;

import co.edu.unbosque.entity.Factura;
import co.edu.unbosque.entity.Suscripcion;
import co.edu.unbosque.model.request.FacturaDTO;
import co.edu.unbosque.model.request.FacturaDTOResponse;
import co.edu.unbosque.repository.FacturaRepository;
import co.edu.unbosque.repository.SuscripcionRepository;
import co.edu.unbosque.entity.enums.EstadoFactura;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FacturaService {

    private final FacturaRepository facturaRepository;
    private final SuscripcionRepository suscripcionRepository;

    public FacturaService(FacturaRepository facturaRepository, SuscripcionRepository suscripcionRepository) {
        this.facturaRepository = facturaRepository;
        this.suscripcionRepository = suscripcionRepository;
    }

    public void emitirFactura(FacturaDTO dto) {

        Suscripcion suscripcion = suscripcionRepository.findById(dto.idSuscripcion())
                .orElseThrow(() -> new RuntimeException("Suscripción no encontrada"));

        Factura factura = new Factura();
        factura.setSuscripcion(suscripcion);
        factura.setTotalFactura(dto.total());
        factura.setEstado(EstadoFactura.PENDIENTE);

        if (dto.fechaEmision() != null) {
            factura.setFechaEmision(
                    dto.fechaEmision().atStartOfDay()
            );
        }

        facturaRepository.save(factura);
    }

    public List<FacturaDTOResponse> obtenerHistorial() {

        return facturaRepository.listarConSuscripcion()
                .stream()
                .map(f -> new FacturaDTOResponse(
                        f.getIdFactura(),
                        f.getTotalFactura(),
                        f.getEstado().name(),
                        f.getSuscripcion().getIdSuscripcion()
                ))
                .toList();
    }

    public List<Factura> filtrarPorEstado(String estado) {
        return facturaRepository.porEstado(estado);
    }
}