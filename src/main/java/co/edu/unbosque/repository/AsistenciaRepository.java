package co.edu.unbosque.repository;

import co.edu.unbosque.entity.Asistencia;
import co.edu.unbosque.entity.Cliente;
import co.edu.unbosque.entity.Sesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {
    Optional<Asistencia> findByClienteAndSesion(Cliente cliente, Sesion sesion);
}
