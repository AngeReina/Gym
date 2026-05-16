package co.edu.unbosque.repository;

import co.edu.unbosque.entity.Clase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClaseRepository extends JpaRepository<Clase, Long> {

    Optional<Clase> findByNombreClase(String nombreClase);

    @Query(value = """
        SELECT *
        FROM clase
        ORDER BY nombre_clase ASC
    """, nativeQuery = true)
    List<Clase> listarOrdenadas();

    @Query(value = """
        SELECT COUNT('a')
        FROM clase
    """, nativeQuery = true)
    Long contarClases();
}