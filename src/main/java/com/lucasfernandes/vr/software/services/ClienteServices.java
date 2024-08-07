package com.lucasfernandes.vr.software.services;

import com.lucasfernandes.vr.software.dto.ClienteDTO;
import com.lucasfernandes.vr.software.entities.Cliente;
import com.lucasfernandes.vr.software.repositories.ClienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteServices {

    private final ClienteRepository clienteRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public ClienteServices(ClienteRepository clienteRepository, ModelMapper modelMapper) {
        this.clienteRepository = clienteRepository;
        this.modelMapper = modelMapper;
    }

    public List<ClienteDTO> getAllClientes() {
        List<Cliente> clientes = clienteRepository.findAll();

        List<ClienteDTO> clientesDTO = new ArrayList<>();

        for (Cliente cliente : clientes) {
            clientesDTO.add(modelMapper.map(cliente, ClienteDTO.class));
        }

        return clientesDTO;
    }

    public ClienteDTO registrarCliente(ClienteDTO clienteDTO) {
        Cliente cliente = clienteRepository.save(modelMapper.map(clienteDTO, Cliente.class));

        return modelMapper.map(cliente, ClienteDTO.class);
    }

    public void deletarClienteById(Long id) {
        clienteRepository.deleteById(id);
    }

    public ClienteDTO atulizarInfoCliente(ClienteDTO clienteDTO) {
        Optional<Cliente> cliente = clienteRepository.findById(clienteDTO.getId());

        if (!cliente.isPresent())
            throw new RuntimeException("Cliente não encontrado: " + clienteDTO.getId());

        cliente.get().setNome(clienteDTO.getNome() == null ? cliente.get().getNome() : clienteDTO.getNome());
        cliente.get().setFechamentoFatura(clienteDTO.getFechamentoFatura() == null ?
                cliente.get().getFechamentoFatura() : clienteDTO.getFechamentoFatura());
        cliente.get().setLimiteCompra(clienteDTO.getLimiteCompra() == null ?
                cliente.get().getLimiteCompra() : clienteDTO.getLimiteCompra());

        clienteRepository.save(cliente.get());

        return modelMapper.map(cliente.get(), ClienteDTO.class);
    }
}
