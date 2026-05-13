package co.edu.unbosque.repository;

import co.edu.unbosque.entity.Sesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface SesionRepository extends JpaRepository<Sesion, Long> {

    List<Sesion> findByFecha(LocalDate fecha);
    List<Sesion> findByCuposDisponiblesGreaterThan(Integer cantidad);
    List<Sesion> findByInstructor_IdInstructorAndFecha(Long idInstructor, LocalDate fecha);
}