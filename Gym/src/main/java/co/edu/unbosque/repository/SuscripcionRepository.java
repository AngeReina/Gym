package co.edu.unbosque.repository;

import co.edu.unbosque.entity.Suscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuscripcionRepository extends JpaRepository<Suscripcion, Long> {


    @Query(value = """
    SELECT
        s.id_suscripcion,
        s.fecha_inicio,
        s.fecha_fin,
        s.estado,
        c.primer_nombre,
        c.primer_apellido,
        p.nombre_plan
    FROM suscripcion s
    JOIN cliente c ON s.id_cliente = c.id_cliente
    JOIN plan p ON s.id_plan = p.id_plan
    ORDER BY s.fecha_inicio DESC
""", nativeQuery = true)
    List<Object[]> listarCompleto();

    @Query(value = """
    SELECT 
        s.id_suscripcion,
        s.fecha_inicio,
        s.fecha_fin,
        s.estado,
        c.primer_nombre,
        c.primer_apellido,
        p.nombre_plan
    FROM suscripcion s
    JOIN cliente c ON s.id_cliente = c.id_cliente
    JOIN plan p ON s.id_plan = p.id_plan
    WHERE s.id_cliente = :idCliente
""", nativeQuery = true)
    List<Object[]> porCliente(@Param("idCliente") Long idCliente);

    @Query(value = """
        SELECT 
            s.id_suscripcion,
            c.primer_nombre,
            p.nombre_plan,
            s.estado
        FROM suscripcion s
        JOIN cliente c ON s.id_cliente = c.id_cliente
        JOIN plan p ON s.id_plan = p.id_plan
        WHERE s.estado = :estado
    """, nativeQuery = true)
    List<Object[]> porEstado(@Param("estado") String estado);

    @Query(value = """
        SELECT COUNT('a') 
        FROM suscripcion
    """, nativeQuery = true)
    Long contarSuscripciones();

    @Query(value = """
        SELECT COUNT('a') 
        FROM suscripcion
        WHERE estado = 'ACTIVO'
    """, nativeQuery = true)
    Long contarActivas();

    @Query(value = """
        SELECT 
            p.nombre_plan,
            COUNT(s.id_suscripcion) AS total
        FROM suscripcion s
        JOIN plan p ON s.id_plan = p.id_plan
        GROUP BY p.nombre_plan
        ORDER BY total DESC
    """, nativeQuery = true)
    List<Object[]> planesMasUsados();
}