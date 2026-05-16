package co.edu.unbosque.service;

import co.edu.unbosque.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportesService {

    private final PagoRepository pagoRepository;
    private final EfectivoRepository efectivoRepository;
    private final TarjetaRepository tarjetaRepository;
    private final TransferenciaRepository transferenciaRepository;
    private final SuscripcionRepository suscripcionRepository;
    private final FacturaRepository facturaRepository;

    public ReportesService(PagoRepository pagoRepository,
                           EfectivoRepository efectivoRepository,
                           TarjetaRepository tarjetaRepository,
                           TransferenciaRepository transferenciaRepository,
                           SuscripcionRepository suscripcionRepository,
                           FacturaRepository facturaRepository) {
        this.pagoRepository          = pagoRepository;
        this.efectivoRepository      = efectivoRepository;
        this.tarjetaRepository       = tarjetaRepository;
        this.transferenciaRepository = transferenciaRepository;
        this.suscripcionRepository   = suscripcionRepository;
        this.facturaRepository       = facturaRepository;
    }

    public List<Object[]> distribucionMetodos() {
        return pagoRepository.distribucionMetodosPago();
    }

    public List<Object[]> rankingClientes() {
        return pagoRepository.rankingClientesPagos();
    }

    public Double totalEfectivo() {
        Double val = efectivoRepository.totalEfectivo();
        return val != null ? val : 0.0;
    }

    public Double totalTarjetas() {
        Double val = tarjetaRepository.totalTarjetas();
        return val != null ? val : 0.0;
    }

    public Double totalTransferencias() {
        Double val = transferenciaRepository.totalTransferencias();
        return val != null ? val : 0.0;
    }

    public Double totalRecaudado() {
        Double val = pagoRepository.totalRecaudado();
        return val != null ? val : 0.0;
    }

    public List<Object[]> rankingBancos() {
        return transferenciaRepository.rankingBancosPorDinero();
    }

    public List<Object[]> franquicias() {
        return tarjetaRepository.usoTarjetasPorFranquicia();
    }

    public List<Object[]> planesMasUsados() {
        return suscripcionRepository.planesMasUsados();
    }

    public Long facturasPagadasCount() {
        Long val = facturaRepository.contarFacturasPagadas();
        return val != null ? val : 0L;
    }

    public Double facturasPagadasTotal() {
        Double val = facturaRepository.totalFacturadoPagado();
        return val != null ? val : 0.0;
    }
}