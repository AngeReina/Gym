package co.edu.unbosque.repository;

import co.edu.unbosque.entity.Clase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ClaseRepository extends JpaRepository<Clase, Long> {

    Optional<Clase> findByNombreClase(String nombreClase);

}