package com.lucasfernandes.vr.software.services;

import com.lucasfernandes.vr.software.dto.PedidoDTO;
import com.lucasfernandes.vr.software.entities.Pedido;
import com.lucasfernandes.vr.software.entities.PedidoProduto;
import com.lucasfernandes.vr.software.entities.Produto;
import com.lucasfernandes.vr.software.repositories.PedidoRepository;
import com.lucasfernandes.vr.software.repositories.ProdutoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoServices {

    private final PedidoRepository pedidoRepository;

    private final ProdutoRepository produtoRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public PedidoServices(PedidoRepository pedidoRepository,
                          ProdutoRepository produtoRepository, ModelMapper modelMapper) {
        this.produtoRepository = produtoRepository;
        this.pedidoRepository = pedidoRepository;
        this.modelMapper = modelMapper;
    }

    public List<PedidoDTO> getAllPedidos() {
        List<Pedido> pedidos = pedidoRepository.findAll();

        List<PedidoDTO> pedidosDTO = new ArrayList<>();

        for (Pedido pedido : pedidos) {
            pedidosDTO.add(modelMapper.map(pedido, PedidoDTO.class));
        }

        return pedidosDTO;
    }

    public PedidoDTO registrarPedido(PedidoDTO pedidoDTO) {
        Pedido pedido = modelMapper.map(pedidoDTO, Pedido.class);

        for (PedidoProduto pedidoProduto : pedido.getProdutos()) {
            Optional<Produto> produto = produtoRepository.findById(pedidoProduto.getProduto().getId());

            if (!produto.isPresent())
                throw new RuntimeException("Produto não encontrado: " + pedidoProduto.getProduto().getId());

            pedidoProduto.setProduto(produto.get());
            pedidoProduto.setPedido(pedido);
        }

        Pedido savedPedido = pedidoRepository.save(pedido);
        return modelMapper.map(savedPedido, PedidoDTO.class);
    }

    public void deletarPedidoById(Long id) {
        pedidoRepository.deleteById(id);
    }

    public PedidoDTO atualizarInfoPedido(PedidoDTO pedidoDTO) {
        Optional<Pedido> pedido = pedidoRepository.findById(pedidoDTO.getId());

        if (!pedido.isPresent())
            throw new RuntimeException("Pedido não encontrado: " + pedidoDTO.getId());

        pedido.get().setCliente(pedidoDTO.getCliente() == null ? pedido.get().getCliente() : pedidoDTO.getCliente());
        pedido.get().setProdutos(pedidoDTO.getProdutos() == null ? pedido.get().getProdutos() : pedidoDTO.getProdutos());
        pedido.get().setStatus(pedidoDTO.getStatus() == null ? pedido.get().getStatus() : pedidoDTO.getStatus());

        pedidoRepository.save(pedido.get());

        return modelMapper.map(pedido.get(), PedidoDTO.class);
    }
}
