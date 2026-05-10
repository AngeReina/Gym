package co.edu.unbosque.repository;

import co.edu.unbosque.entity.Factura;
import co.edu.unbosque.entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
    Optional<Pago> findByFactura(Factura factura);
}
