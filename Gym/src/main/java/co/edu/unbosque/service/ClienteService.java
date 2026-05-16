package co.edu.unbosque.service;

import co.edu.unbosque.entity.Cliente;
import co.edu.unbosque.model.request.ClienteDTO;
import co.edu.unbosque.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public void registrar(ClienteDTO dto) {
        Cliente c = new Cliente();
        c.setPrimerNombre(dto.primerNombre());
        c.setSegundoNombre(dto.segundoNombre());
        c.setPrimerApellido(dto.primerApellido());
        c.setSegundoApellido(dto.segundoApellido());
        c.setNumeroDocumento(dto.numeroDocumento());
        c.setTipoDocumento(dto.tipoDocumento());
        c.setTelefono(dto.telefono());
        c.setEmail(dto.email());

        clienteRepository.save(c);
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.listarOrdenados();
    }

    public List<Object[]> obtenerRanking() {
        return clienteRepository.rankingClientesAsistencia();
    }
}