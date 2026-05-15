package co.edu.unbosque.repository;

import co.edu.unbosque.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {

    Optional<Instructor> findByNumeroDocumento(String numeroDocumento);

    @Query(value = """
        SELECT *
        FROM instructor
        WHERE especialidad = :especialidad
    """, nativeQuery = true)
    List<Instructor> porEspecialidad(@Param("especialidad") String especialidad);

    @Query(value = """
        SELECT COUNT('a')
        FROM instructor
    """, nativeQuery = true)
    Long contarInstructores();

    @Query(value = """
        SELECT *
        FROM instructor
        WHERE primer_nombre ILIKE CONCAT('%', :nombre, '%')
    """, nativeQuery = true)
    List<Instructor> buscarPorNombre(@Param("nombre") String nombre);

    @Query(value = """
        SELECT DISTINCT i.*
        FROM instructor i
        JOIN sesion s ON s.id_instructor = i.id_instructor
    """, nativeQuery = true)
    List<Instructor> instructoresConClases();

    @Query(value = """
        SELECT i.id_instructor, i.primer_nombre, COUNT(s.id_sesion) AS total_sesiones
        FROM instructor i
        JOIN sesion s ON s.id_instructor = i.id_instructor
        GROUP BY i.id_instructor, i.primer_nombre
        ORDER BY total_sesiones DESC
    """, nativeQuery = true)
    List<Object[]> sesionesPorInstructor();
}