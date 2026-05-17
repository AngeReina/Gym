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

        if (pagoRepository.findByFactura(factura).isPresent()) {
            throw new RuntimeException("La factura ya fue pagada");
        }

        double suma = pagoDTO.metodos().stream()
                .mapToDouble(DetalleMetodoDTO::monto)
                .sum();

        if (Math.abs(suma - pagoDTO.totalRecibo()) > 0.01) {
            throw new RuntimeException("El total no coincide con los métodos de pago");
        }

        Pago nuevoPago = new Pago();
        nuevoPago.setTotalRecibo(pagoDTO.totalRecibo());
        nuevoPago.setFactura(factura);

        pagoRepository.save(nuevoPago);

        for (DetalleMetodoDTO detalle : pagoDTO.metodos()) {

            switch (detalle.tipoMetodo().toUpperCase()) {

                case "EFECTIVO" -> {

                    Efectivo e = new Efectivo(
                            detalle.monto(),
                            nuevoPago
                    );

                    efectivoRepository.save(e);
                }

                case "TARJETA" -> {

                    Tarjeta t = new Tarjeta();

                    t.setMonto(detalle.monto());

                    try {

                        t.setFranquicia(
                                FranquiciaTarjeta.valueOf(
                                        detalle.franquicia().trim().toUpperCase()
                                )
                        );

                        t.setTipo(
                                TipoTarjeta.valueOf(
                                        detalle.tipoTarjeta().trim().toUpperCase()
                                )
                        );

                    } catch (Exception e) {

                        throw new RuntimeException("Tipo de tarjeta inválido");
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

                default -> throw new RuntimeException("Método de pago inválido");
            }
        }

        factura.setEstado(EstadoFactura.PAGADA);

        facturaRepository.save(factura);
    }
}