package co.edu.unbosque.repository;

import co.edu.unbosque.entity.Factura;
import co.edu.unbosque.entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {

    Optional<Pago> findByFactura(Factura factura);

    @Query(value = """
        SELECT p.*
        FROM pago p
        JOIN factura f ON p.id_factura = f.id_factura
    """, nativeQuery = true)
    List<Pago> listarConFactura();

    @Query(value = """
        SELECT COALESCE(SUM(p.total_recibo), 0)
        FROM pago p
    """, nativeQuery = true)
    Double totalRecaudado();

    @Query(value = """
        SELECT COUNT('X')
        FROM pago p
    """, nativeQuery = true)
    Long contarPagos();

    @Query(value = """
        SELECT p.*
        FROM pago p
        JOIN factura f ON p.id_factura = f.id_factura
        WHERE f.estado = 'PAGADA'
    """, nativeQuery = true)
    List<Pago> pagosDeFacturasPagadas();

    @Query(value = """
        SELECT p.id_factura, SUM(p.total_recibo) AS total
        FROM pago p
        GROUP BY p.id_factura
    """, nativeQuery = true)
    List<Object[]> totalPorFactura();

    @Query(value = """
        SELECT p.id_factura, COUNT('X') AS cantidad
        FROM pago p
        GROUP BY p.id_factura
    """, nativeQuery = true)
    List<Object[]> cantidadPagosPorFactura();

    @Query(value = """
        SELECT c.id_cliente, c.primer_nombre, SUM(p.total_recibo) AS total_pagado
        FROM pago p
        JOIN factura f ON p.id_factura = f.id_factura
        JOIN suscripcion s ON f.id_suscripcion = s.id_suscripcion
        JOIN cliente c ON s.id_cliente = c.id_cliente
        GROUP BY c.id_cliente, c.primer_nombre
        ORDER BY total_pagado DESC
    """, nativeQuery = true)
    List<Object[]> rankingClientesPagos();

    @Query(value = """
        SELECT 'EFECTIVO' AS metodo, COUNT('X') 
        FROM efectivo
        UNION ALL
        SELECT 'TARJETA', COUNT('X') 
        FROM tarjeta
        UNION ALL
        SELECT 'TRANSFERENCIA', COUNT('X') 
        FROM transferencia
    """, nativeQuery = true)
    List<Object[]> distribucionMetodosPago();
}