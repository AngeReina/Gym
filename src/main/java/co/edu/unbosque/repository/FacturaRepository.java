package co.edu.unbosque.repository;

import co.edu.unbosque.entity.Factura;
import co.edu.unbosque.entity.enums.EstadoFactura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {
    List<Factura> findByEstado(EstadoFactura estado);
}
