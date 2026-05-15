package co.edu.unbosque.repository;

import co.edu.unbosque.entity.Transferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferenciaRepository extends JpaRepository<Transferencia, Long> {

    @Query("SELECT tr FROM Transferencia tr JOIN FETCH tr.pago")
    List<Transferencia> listarConPago();

    @Query("SELECT SUM(tr.monto) FROM Transferencia tr")
    Double totalTransferencias();

    @Query("""
        SELECT tr.bancoOrigen, COUNT(tr)
        FROM Transferencia tr
        GROUP BY tr.bancoOrigen
        ORDER BY COUNT(tr) DESC
    """)
    List<Object[]> usoTransferenciasPorBanco();

    @Query("""
        SELECT tr.bancoOrigen, AVG(tr.monto)
        FROM Transferencia tr
        GROUP BY tr.bancoOrigen
    """)
    List<Object[]> promedioPorBanco();

    @Query("""
        SELECT tr.bancoOrigen, SUM(tr.monto)
        FROM Transferencia tr
        GROUP BY tr.bancoOrigen
        ORDER BY SUM(tr.monto) DESC
    """)
    List<Object[]> rankingBancosPorDinero();
}