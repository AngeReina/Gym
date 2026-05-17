package co.edu.unbosque.repository;

import co.edu.unbosque.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByNumeroDocumento(String numeroDocumento);

    @Query(value = """
        SELECT *
        FROM cliente
        ORDER BY primer_apellido ASC
    """, nativeQuery = true)
    List<Cliente> listarOrdenados();

    @Query(value = """
        SELECT COUNT('X')
        FROM cliente
    """, nativeQuery = true)
    Long contarClientes();

    @Query(value = """
        SELECT *
        FROM cliente
    """, nativeQuery = true)
    List<Cliente> listarCompleto();

    @Query(value = """
        SELECT a.id_cliente, COUNT('X')
        FROM asistencia a
        GROUP BY a.id_cliente
        ORDER BY COUNT('a') DESC
    """, nativeQuery = true)
    List<Object[]> rankingClientesAsistencia();
}