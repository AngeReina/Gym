package co.edu.unbosque.service;

import co.edu.unbosque.entity.*;
import co.edu.unbosque.entity.enums.*;
import co.edu.unbosque.model.request.DetalleMetodoDTO;
import co.edu.unbosque.model.request.PagoDTO;
import co.edu.unbosque.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class PagoService {

    private final PagoRepository pagoRepository;
    private final FacturaRepository facturaRepository;
    private final EfectivoRepository efectivoRepository;
    private final TarjetaRepository tarjetaRepository;
    private final TransferenciaRepository transferenciaRepository;

    public PagoService(PagoRepository pagoRepository, FacturaRepository facturaRepository,
                       EfectivoRepository efectivoRepository, TarjetaRepository tarjetaRepository,
                       TransferenciaRepository transferenciaRepository) {
        this.pagoRepository = pagoRepository;
        this.facturaRepository = facturaRepository;
        this.efectivoRepository = efectivoRepository;
        this.tarjetaRepository = tarjetaRepository;
        this.transferenciaRepository = transferenciaRepository;
    }

    @Transactional
    public void registrarPagoMixto(PagoDTO pagoDTO) {
        Factura factura = facturaRepository.findById(pagoDTO.idFactura())
                .orElseThrow(() -> new RuntimeException("Factura no encontrada"));

        Pago nuevoPago = new Pago();
        nuevoPago.setTotalRecibo(pagoDTO.totalRecibo());
        nuevoPago.setFactura(factura);
        pagoRepository.save(nuevoPago);

        for (DetalleMetodoDTO detalle : pagoDTO.metodos()) {
            switch (detalle.tipoMetodo().toUpperCase()) {
                case "EFECTIVO" -> {
                    Efectivo e = new Efectivo(detalle.monto(), nuevoPago);
                    efectivoRepository.save(e);
                }
                case "TARJETA" -> {
                    Tarjeta t = new Tarjeta();
                    t.setMonto(detalle.monto());

                    if (detalle.franquicia() != null) {
                        t.setFranquicia(FranquiciaTarjeta.valueOf(detalle.franquicia().toUpperCase()));
                    }
                    if (detalle.tipoTarjeta() != null) {
                        t.setTipo(TipoTarjeta.valueOf(detalle.tipoTarjeta().toUpperCase()));
                    }

                    t.setPago(nuevoPago);
                    tarjetaRepository.save(t);
                }
                case "TRANSFERENCIA" -> {
                    Transferencia tr = new Transferencia();
                    tr.setMonto(detalle.monto());
                    tr.setBancoOrigen(detalle.bancoOrigen());
                    tr.setNumeroTransaccion(detalle.numTransaccion());
                    tr.setPago(nuevoPago);
                    transferenciaRepository.save(tr);
                }
            }
        }

        factura.setEstado(EstadoFactura.PAGADA);
        facturaRepository.save(factura);
    }
}