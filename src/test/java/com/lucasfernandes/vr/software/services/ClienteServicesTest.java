package com.lucasfernandes.vr.software.services;

import com.lucasfernandes.vr.software.dto.ClienteDTO;
import com.lucasfernandes.vr.software.entities.Cliente;
import com.lucasfernandes.vr.software.repositories.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteServicesTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ClienteServices clienteServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnAllClientes() {
        Cliente cliente1 = new Cliente();
        Cliente cliente2 = new Cliente();
        when(clienteRepository.findAll()).thenReturn(Arrays.asList(cliente1, cliente2));
        when(modelMapper.map(any(Cliente.class), eq(ClienteDTO.class))).thenReturn(new ClienteDTO());

        List<ClienteDTO> clientesDTO = clienteServices.getAllClientes();

        assertEquals(2, clientesDTO.size());
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnClienteRegistrado() {
        ClienteDTO clienteDTO = new ClienteDTO();
        Cliente cliente = new Cliente();
        when(modelMapper.map(clienteDTO, Cliente.class)).thenReturn(cliente);
        when(clienteRepository.save(cliente)).thenReturn(cliente);
        when(modelMapper.map(cliente, ClienteDTO.class)).thenReturn(clienteDTO);

        ClienteDTO savedClienteDTO = clienteServices.registrarCliente(clienteDTO);

        assertNotNull(savedClienteDTO);
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    void shouldDeletarClienteById() {
        clienteServices.deletarClienteById(1L);
        verify(clienteRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldAtualizarInfoCliente() {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(1L);
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(modelMapper.map(cliente, ClienteDTO.class)).thenReturn(clienteDTO);

        ClienteDTO updatedClienteDTO = clienteServices.atulizarInfoCliente(clienteDTO);

        assertNotNull(updatedClienteDTO);
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    void testAtualizarInfoClienteNotFound() {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(1L);
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            clienteServices.atulizarInfoCliente(clienteDTO);
        });

        assertEquals("Cliente não encontrado: " + clienteDTO.getId(), exception.getMessage());
        verify(clienteRepository, times(1)).findById(1L);
        verify(clienteRepository, never()).save(any(Cliente.class));
    }
}