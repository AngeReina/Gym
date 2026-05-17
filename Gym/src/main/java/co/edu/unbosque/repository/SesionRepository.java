package co.edu.unbosque.repository;

import co.edu.unbosque.entity.Sesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SesionRepository extends JpaRepository<Sesion, Long> {

    @Query(value = """
        SELECT
            s.id_sesion,
            c.nombre_clase,
            CONCAT(i.primer_nombre, ' ', i.primer_apellido) AS instructor,
            s.fecha,
            s.hora_inicio,
            s.hora_fin,
            s.cupo_max,
            s.cupos_disponibles
        FROM sesion s
        JOIN clase c ON s.id_clase = c.id_clase
        JOIN instructor i ON s.id_instructor = i.id_instructor
        ORDER BY s.fecha DESC
    """, nativeQuery = true)
    List<Object[]> listarCompleto();

    @Query(value = """
        SELECT 
            s.id_sesion,
            s.fecha,
            c.nombre_clase,
            i.primer_nombre,
            i.primer_apellido
        FROM sesion s
        JOIN clase c ON s.id_clase = c.id_clase
        JOIN instructor i ON s.id_instructor = i.id_instructor
        WHERE s.fecha = :fecha
    """, nativeQuery = true)
    List<Object[]> porFecha(@Param("fecha") LocalDate fecha);

    @Query(value = """
        SELECT 
            s.id_sesion,
            s.fecha,
            c.nombre_clase,
            s.cupos_disponibles
        FROM sesion s
        JOIN clase c ON s.id_clase = c.id_clase
        WHERE s.id_instructor = :idInstructor
        ORDER BY s.fecha DESC
    """, nativeQuery = true)
    List<Object[]> porInstructor(@Param("idInstructor") Long idInstructor);

    @Query(value = """
        SELECT 
            s.id_sesion,
            s.fecha,
            c.nombre_clase,
            s.cupos_disponibles
        FROM sesion s
        JOIN clase c ON s.id_clase = c.id_clase
        WHERE s.cupos_disponibles > 0
        ORDER BY s.fecha ASC
    """, nativeQuery = true)
    List<Object[]> conCuposDisponibles();

    @Query(value = """
        SELECT 
            s.id_sesion,
            COUNT(a.id_asistencia) AS total_asistencias
        FROM sesion s
        JOIN asistencia a ON a.id_sesion = s.id_sesion
        GROUP BY s.id_sesion
        ORDER BY total_asistencias DESC
    """, nativeQuery = true)
    List<Object[]> sesionesMasAsistidas();

    @Query(value = """
        SELECT COUNT('X') 
        FROM sesion
    """, nativeQuery = true)
    Long contarSesiones();

    @Query(value = """
        SELECT COUNT('X') 
        FROM sesion 
        WHERE cupos_disponibles > 0
    """, nativeQuery = true)
    Long contarSesionesConCupos();
}