package com.lucasfernandes.vr.software.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucasfernandes.vr.software.dto.ProdutoDTO;
import com.lucasfernandes.vr.software.services.ProdutoServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProdutoController.class)
class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProdutoServices produtoServices;

    @Autowired
    private ObjectMapper objectMapper;

    private ProdutoDTO produtoDTO;

    @BeforeEach
    public void setUp() {
        produtoDTO = new ProdutoDTO();
        produtoDTO.setId(1L);
        produtoDTO.setDescricao("Produto Teste");
        produtoDTO.setPreco(50.0);
    }

    @Test
    void shouldReturnAllProdutos() throws Exception {
        List<ProdutoDTO> produtos = Arrays.asList(produtoDTO);

        Mockito.when(produtoServices.getAllProdutos()).thenReturn(produtos);

        mockMvc.perform(get("/produtos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(produtoDTO.getId()))
                .andExpect(jsonPath("$[0].descricao").value(produtoDTO.getDescricao()))
                .andExpect(jsonPath("$[0].preco").value(produtoDTO.getPreco()));
    }

    @Test
    void shouldReturnProdutoRegistrado() throws Exception {
        Mockito.when(produtoServices.registrarProduto(any(ProdutoDTO.class))).thenReturn(produtoDTO);

        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produtoDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(produtoDTO.getId()))
                .andExpect(jsonPath("$.descricao").value(produtoDTO.getDescricao()))
                .andExpect(jsonPath("$.preco").value(produtoDTO.getPreco()));
    }

    @Test
    void shouldDeletarProdutoById() throws Exception {
        Mockito.doNothing().when(produtoServices).deletarProdutoById(anyLong());

        mockMvc.perform(delete("/produtos/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Produto excluido com sucesso!"));
    }

    @Test
    void shouldAtualizarInfoProduto() throws Exception {
        Mockito.when(produtoServices.atualizarInfoProduto(any(ProdutoDTO.class))).thenReturn(produtoDTO);

        mockMvc.perform(put("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produtoDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(produtoDTO.getId()))
                .andExpect(jsonPath("$.descricao").value(produtoDTO.getDescricao()))
                .andExpect(jsonPath("$.preco").value(produtoDTO.getPreco()));
    }
}