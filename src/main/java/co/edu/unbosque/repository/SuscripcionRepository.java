package co.edu.unbosque.repository;

import co.edu.unbosque.entity.Cliente;
import co.edu.unbosque.entity.Suscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuscripcionRepository extends JpaRepository<Suscripcion, Long> {
    List<Suscripcion> findByCliente(Cliente cliente);
    List<Suscripcion> findByClienteAndEstado(Cliente cliente, String estado);
}