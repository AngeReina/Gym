package co.edu.unbosque.repository;

import co.edu.unbosque.entity.Asistencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {
    List<Asistencia> findByCliente_IdCliente(Long idCliente);
    List<Asistencia> findBySesion_IdSesion(Long idSesion);
}