package com.lucasfernandes.vr.software.services;

import com.lucasfernandes.vr.software.dto.ProdutoDTO;
import com.lucasfernandes.vr.software.entities.Produto;
import com.lucasfernandes.vr.software.repositories.ProdutoRepository;
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

class ProdutoServicesTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProdutoServices produtoServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnAllProdutos() {
        Produto produto1 = new Produto();
        Produto produto2 = new Produto();
        when(produtoRepository.findAll()).thenReturn(Arrays.asList(produto1, produto2));
        when(modelMapper.map(any(Produto.class), eq(ProdutoDTO.class))).thenReturn(new ProdutoDTO());

        List<ProdutoDTO> produtosDTO = produtoServices.getAllProdutos();

        assertEquals(2, produtosDTO.size());
        verify(produtoRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnProdutoRegistrado() {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        Produto produto = new Produto();
        when(modelMapper.map(produtoDTO, Produto.class)).thenReturn(produto);
        when(produtoRepository.save(produto)).thenReturn(produto);
        when(modelMapper.map(produto, ProdutoDTO.class)).thenReturn(produtoDTO);

        ProdutoDTO savedProdutoDTO = produtoServices.registrarProduto(produtoDTO);

        assertNotNull(savedProdutoDTO);
        verify(produtoRepository, times(1)).save(produto);
    }

    @Test
    void shouldDeletarProdutoById() {
        produtoServices.deletarProdutoById(1L);
        verify(produtoRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldAtualizarInfoProduto() {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setId(1L);
        Produto produto = new Produto();
        produto.setId(1L);
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(modelMapper.map(produto, ProdutoDTO.class)).thenReturn(produtoDTO);

        ProdutoDTO updatedProdutoDTO = produtoServices.atualizarInfoProduto(produtoDTO);

        assertNotNull(updatedProdutoDTO);
        verify(produtoRepository, times(1)).save(produto);
    }

    @Test
    void shouldReturnProdutoNaoEncontradoParaAtualizarInfo() {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setId(1L);
        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            produtoServices.atualizarInfoProduto(produtoDTO);
        });

        assertEquals("Produto não encontrado: " + produtoDTO.getId(), exception.getMessage());
        verify(produtoRepository, times(1)).findById(1L);
        verify(produtoRepository, never()).save(any(Produto.class));
    }
}