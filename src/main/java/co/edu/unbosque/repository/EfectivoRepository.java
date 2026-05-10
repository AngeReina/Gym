package co.edu.unbosque.repository;

import co.edu.unbosque.entity.Efectivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EfectivoRepository extends JpaRepository<Efectivo, Long> {
}
