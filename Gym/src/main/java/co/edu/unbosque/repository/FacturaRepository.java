package co.edu.unbosque.repository;

import co.edu.unbosque.entity.Factura;
import co.edu.unbosque.entity.enums.EstadoFactura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {

    @Query(value = """
        SELECT f.*
        FROM factura f
    """, nativeQuery = true)
    List<Factura> listarTodas();

    @Query(value = """
        SELECT f.*
        FROM factura f
        JOIN suscripcion s ON f.id_suscripcion = s.id_suscripcion
    """, nativeQuery = true)
    List<Factura> listarConSuscripcion();

    @Query(value = """
        SELECT f.*
        FROM factura f
        WHERE f.estado = CAST(:estado AS varchar)
    """, nativeQuery = true)
    List<Factura> porEstado(@Param("estado") String estado);

    @Query(value = """
        SELECT COUNT('X')
        FROM factura f
        WHERE f.estado = 'PAGADA'
    """, nativeQuery = true)
    Long contarFacturasPagadas();

    @Query(value = """
        SELECT COALESCE(SUM(f.total_factura), 0)
        FROM factura f
        WHERE f.estado = 'PAGADA'
    """, nativeQuery = true)
    Double totalFacturadoPagado();
}