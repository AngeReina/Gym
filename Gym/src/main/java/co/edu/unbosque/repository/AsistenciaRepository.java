package co.edu.unbosque.repository;

import co.edu.unbosque.entity.Asistencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {

    @Query(value = """
        SELECT 
            a.id_asistencia,
            a.fecha_registro,
            a.estado_asistencia,
            c.primer_nombre,
            c.primer_apellido,
            a.id_sesion
        FROM asistencia a
        INNER JOIN cliente c ON a.id_cliente = c.id_cliente
        ORDER BY a.fecha_registro DESC
    """, nativeQuery = true)
    List<Object[]> listarCompleto();

    @Query(value = """
        SELECT 
            a.id_asistencia,
            a.fecha_registro,
            a.estado_asistencia,
            c.primer_nombre,
            c.primer_apellido,
            a.id_sesion
        FROM asistencia a
        INNER JOIN cliente c ON a.id_cliente = c.id_cliente
        WHERE a.id_sesion = :id
        ORDER BY a.fecha_registro DESC
    """, nativeQuery = true)
    List<Object[]> porSesion(@Param("id") Long id);
}