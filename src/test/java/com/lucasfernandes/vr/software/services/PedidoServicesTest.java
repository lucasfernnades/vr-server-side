package com.lucasfernandes.vr.software.services;

import com.lucasfernandes.vr.software.dto.ClienteDTO;
import com.lucasfernandes.vr.software.dto.PedidoDTO;
import com.lucasfernandes.vr.software.entities.Cliente;
import com.lucasfernandes.vr.software.entities.Pedido;
import com.lucasfernandes.vr.software.entities.PedidoProduto;
import com.lucasfernandes.vr.software.entities.Produto;
import com.lucasfernandes.vr.software.repositories.PedidoRepository;
import com.lucasfernandes.vr.software.repositories.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PedidoServicesTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PedidoServices pedidoServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnAllPedidos() {
        Pedido pedido1 = new Pedido();
        Pedido pedido2 = new Pedido();
        when(pedidoRepository.findAll()).thenReturn(Arrays.asList(pedido1, pedido2));
        when(modelMapper.map(any(Pedido.class), eq(PedidoDTO.class))).thenReturn(new PedidoDTO());

        List<PedidoDTO> pedidosDTO = pedidoServices.getAllPedidos();

        assertEquals(2, pedidosDTO.size());
        verify(pedidoRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnPedidoRegistrado() {
        PedidoDTO pedidoDTO = new PedidoDTO();
        Pedido pedido = new Pedido();
        PedidoProduto pedidoProduto = new PedidoProduto();
        Produto produto = new Produto();
        produto.setId(1L);
        pedidoProduto.setProduto(produto);
        List<PedidoProduto> pedidoProdutos = new ArrayList<>();
        pedidoProdutos.add(pedidoProduto);
        pedido.setProdutos(pedidoProdutos);

        when(modelMapper.map(pedidoDTO, Pedido.class)).thenReturn(pedido);
        when(produtoRepository.findById(anyLong())).thenReturn(Optional.of(produto));
        when(pedidoRepository.save(pedido)).thenReturn(pedido);
        when(modelMapper.map(pedido, PedidoDTO.class)).thenReturn(pedidoDTO);

        PedidoDTO savedPedidoDTO = pedidoServices.registrarPedido(pedidoDTO);

        assertNotNull(savedPedidoDTO);
        verify(pedidoRepository, times(1)).save(pedido);
    }

    @Test
    void shouldReturnProdutoNaoEncontradoParaRegistrarNoPedido() {
        PedidoDTO pedidoDTO = new PedidoDTO();
        Pedido pedido = new Pedido();
        PedidoProduto pedidoProduto = new PedidoProduto();
        Produto produto = new Produto();
        produto.setId(1L);
        List<PedidoProduto> pedidoProdutos = new ArrayList<>();
        pedidoProdutos.add(pedidoProduto);
        pedidoProduto.setProduto(produto);
        pedido.setProdutos(pedidoProdutos);

        when(modelMapper.map(pedidoDTO, Pedido.class)).thenReturn(pedido);
        when(produtoRepository.findById(produto.getId())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pedidoServices.registrarPedido(pedidoDTO);
        });

        assertEquals("Produto não encontrado: " + pedidoProduto.getProduto().getId(), exception.getMessage());

        verify(produtoRepository, times(1)).findById(produto.getId());
        verify(pedidoRepository, times(0)).save(any(Pedido.class));
    }


    @Test
    void shouldDeletarPedidoById() {
        pedidoServices.deletarPedidoById(1L);
        verify(pedidoRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldAtualizarInfoPedido() {
        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setId(1L);
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        when(modelMapper.map(pedido, PedidoDTO.class)).thenReturn(pedidoDTO);

        PedidoDTO updatedPedidoDTO = pedidoServices.atualizarInfoPedido(pedidoDTO);

        assertNotNull(updatedPedidoDTO);
        verify(pedidoRepository, times(1)).save(pedido);
    }

    @Test
    void shouldReturnPedidoNaoEncontradoParaAtualizarInfo() {
        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setId(1L);
        when(pedidoRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pedidoServices.atualizarInfoPedido(pedidoDTO);
        });

        assertEquals("Pedido não encontrado: " + pedidoDTO.getId(), exception.getMessage());
        verify(pedidoRepository, times(1)).findById(1L);
        verify(pedidoRepository, never()).save(any(Pedido.class));
    }
}
