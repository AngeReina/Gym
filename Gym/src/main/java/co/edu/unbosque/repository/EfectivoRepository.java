package co.edu.unbosque.repository;

import co.edu.unbosque.entity.Efectivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EfectivoRepository extends JpaRepository<Efectivo, Long> {

    @Query(value = """
        SELECT e.*
        FROM efectivo e
        JOIN pago p ON e.id_pago = p.id_pago
    """, nativeQuery = true)
    List<Efectivo> listarConPago();

    @Query(value = """
        SELECT COALESCE(SUM(e.monto), 0)
        FROM efectivo e
    """, nativeQuery = true)
    Double totalEfectivo();

    @Query(value = """
        SELECT COUNT('a')
        FROM efectivo e
    """, nativeQuery = true)
    Long contarEfectivos();

    @Query(value = """
        SELECT DISTINCT c.*
        FROM cliente c
        JOIN suscripcion s ON s.id_cliente = c.id_cliente
        JOIN factura f ON f.id_suscripcion = s.id_suscripcion
        JOIN pago p ON p.id_factura = f.id_factura
        JOIN efectivo e ON e.id_pago = p.id_pago
    """, nativeQuery = true)
    List<Object[]> clientesQuePaganEfectivo();

    @Query(value = """
        SELECT c.id_cliente, c.primer_nombre, SUM(e.monto) AS total_efectivo
        FROM cliente c
        JOIN suscripcion s ON s.id_cliente = c.id_cliente
        JOIN factura f ON f.id_suscripcion = s.id_suscripcion
        JOIN pago p ON p.id_factura = f.id_factura
        JOIN efectivo e ON e.id_pago = p.id_pago
        GROUP BY c.id_cliente, c.primer_nombre
        ORDER BY total_efectivo DESC
    """, nativeQuery = true)
    List<Object[]> totalEfectivoPorCliente();
}