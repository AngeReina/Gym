package co.edu.unbosque.repository;

import co.edu.unbosque.entity.Sesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SesionRepository extends JpaRepository<Sesion, Long> {
    List<Sesion> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);
}
