package com.lucasfernandes.vr.software.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucasfernandes.vr.software.dto.ClienteDTO;
import com.lucasfernandes.vr.software.services.ClienteServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteServices clienteServices;

    @Autowired
    private ObjectMapper objectMapper;

    private ClienteDTO clienteDTO;

    @BeforeEach
    public void setup() {
        clienteDTO = new ClienteDTO();
        clienteDTO.setId(1L);
        clienteDTO.setNome("Cliente Teste");
        clienteDTO.setFechamentoFatura(12);
        clienteDTO.setLimiteCompra(3000F);
    }

    @Test
    void shouldReturnAllClientes() throws Exception {
        List<ClienteDTO> clienteDTOList = Collections.singletonList(clienteDTO);

        when(clienteServices.getAllClientes()).thenReturn(clienteDTOList);

        mockMvc.perform(get("/clientes")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(clienteDTOList)));
    }

    @Test
    void shouldReturnClienteRegistrado() throws Exception {
        when(clienteServices.registrarCliente(any(ClienteDTO.class))).thenReturn(clienteDTO);

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(clienteDTO)));
    }

    @Test
    void shouldDeleteClienteById() throws Exception {
        doNothing().when(clienteServices).deletarClienteById(anyLong());

        mockMvc.perform(delete("/clientes/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("Cliente excluido com sucesso!"));
    }

    @Test
    void shouldAtualizarInfoCliente() throws Exception {
        when(clienteServices.atulizarInfoCliente(any(ClienteDTO.class))).thenReturn(clienteDTO);

        mockMvc.perform(put("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(clienteDTO)));
    }
}