package com.lucasfernandes.vr.software.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucasfernandes.vr.software.dto.PedidoDTO;
import com.lucasfernandes.vr.software.entities.Cliente;
import com.lucasfernandes.vr.software.services.PedidoServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PedidoController.class)
class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PedidoServices pedidoServices;

    @InjectMocks
    private PedidoController pedidoController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    void shouldReturnAllPedidos() throws Exception {
        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setId(1L);
        pedidoDTO.setCliente(new Cliente());
        pedidoDTO.setProdutos(new ArrayList<>());
        pedidoDTO.setStatus(true);

        List<PedidoDTO> pedidos = Collections.singletonList(pedidoDTO);

        when(pedidoServices.getAllPedidos()).thenReturn(pedidos);

        mockMvc.perform(get("/pedidos")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(pedidos)));
    }

    @Test
    void shouldreturnPedidoRegistrado() throws Exception {
        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setId(1L);
        pedidoDTO.setCliente(new Cliente());
        pedidoDTO.setProdutos(new ArrayList<>());
        pedidoDTO.setStatus(true);

        when(pedidoServices.registrarPedido(any(PedidoDTO.class))).thenReturn(pedidoDTO);

        mockMvc.perform(post("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedidoDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(pedidoDTO)));
    }

    @Test
    void shouldDeletePedidoById() throws Exception {
        doNothing().when(pedidoServices).deletarPedidoById(anyLong());

        mockMvc.perform(delete("/pedidos/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Pedido excluido com sucesso!"));
    }

    @Test
    void shouldAtualizarInfoPedido() throws Exception {
        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setId(1L);
        pedidoDTO.setCliente(new Cliente());
        pedidoDTO.setProdutos(new ArrayList<>());
        pedidoDTO.setStatus(true);

        when(pedidoServices.atualizarInfoPedido(any(PedidoDTO.class))).thenReturn(pedidoDTO);

        mockMvc.perform(put("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedidoDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(pedidoDTO)));
    }
}
