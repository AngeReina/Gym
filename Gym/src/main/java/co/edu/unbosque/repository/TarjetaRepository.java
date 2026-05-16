package co.edu.unbosque.repository;

import co.edu.unbosque.entity.Pago;
import co.edu.unbosque.entity.Tarjeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarjetaRepository extends JpaRepository<Tarjeta, Long> {

    List<Tarjeta> findByPago(Pago pago);

    @Query("SELECT t FROM Tarjeta t JOIN FETCH t.pago")
    List<Tarjeta> listarConPago();

    @Query("SELECT SUM(t.monto) FROM Tarjeta t")
    Double totalTarjetas();

    @Query("""
        SELECT t.franquicia, COUNT(t)
        FROM Tarjeta t
        GROUP BY t.franquicia
    """)
    List<Object[]> usoTarjetasPorFranquicia();

    @Query("""
        SELECT t.franquicia, AVG(t.monto)
        FROM Tarjeta t
        GROUP BY t.franquicia
    """)
    List<Object[]> promedioPorFranquicia();

    @Query("""
        SELECT p.factura.suscripcion.cliente.idCliente, COUNT(t)
        FROM Tarjeta t
        JOIN t.pago p
        GROUP BY p.factura.suscripcion.cliente.idCliente
        ORDER BY COUNT(t) DESC
    """)
    List<Object[]> clientesTopTarjeta();
}
