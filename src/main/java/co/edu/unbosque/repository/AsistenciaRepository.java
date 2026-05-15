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
        SELECT a.*
        FROM asistencia a
        INNER JOIN cliente c ON a.id_cliente = c.id_cliente
        INNER JOIN sesion s ON a.id_sesion = s.id_sesion
        ORDER BY a.fecha_registro DESC
    """, nativeQuery = true)
    List<Asistencia> listarCompleto();

    @Query(value = """
        SELECT a.*
        FROM asistencia a
        WHERE a.id_sesion = :id
    """, nativeQuery = true)
    List<Asistencia> porSesion(@Param("id") Long id);
}