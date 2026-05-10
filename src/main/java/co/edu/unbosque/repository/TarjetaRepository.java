package co.edu.unbosque.repository;

import co.edu.unbosque.entity.Pago;
import co.edu.unbosque.entity.Tarjeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarjetaRepository extends JpaRepository<Tarjeta, Long> {
    List<Tarjeta> findByPago(Pago pago);
}
