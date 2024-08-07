package com.lucasfernandes.vr.software.services;

import com.lucasfernandes.vr.software.dto.ProdutoDTO;
import com.lucasfernandes.vr.software.entities.Produto;
import com.lucasfernandes.vr.software.repositories.ProdutoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoServices {

    private final ProdutoRepository produtoRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public ProdutoServices(ProdutoRepository produtoRepository, ModelMapper modelMapper) {
        this.produtoRepository = produtoRepository;
        this.modelMapper = modelMapper;
    }

    public List<ProdutoDTO> getAllProdutos() {
        List<Produto> produtos = produtoRepository.findAll();

        List<ProdutoDTO> produtosDTO = new ArrayList<>();

        for (Produto produto : produtos) {
            produtosDTO.add(modelMapper.map(produto, ProdutoDTO.class));
        }

        return produtosDTO;
    }

    public ProdutoDTO registrarProduto(ProdutoDTO produtoDTO) {
        Produto produto = produtoRepository.save(modelMapper.map(produtoDTO, Produto.class));

        return modelMapper.map(produto, ProdutoDTO.class);
    }

    public void deletarProdutoById(Long id) {
        produtoRepository.deleteById(id);
    }

    public ProdutoDTO atualizarInfoProduto(ProdutoDTO produtoDTO) {
        Optional<Produto> produto = produtoRepository.findById(produtoDTO.getId());

        if (!produto.isPresent())
            throw new RuntimeException("Produto não encontrado: " + produtoDTO.getId());

        produto.get().setDescricao(produtoDTO.getDescricao() == null ?
                produto.get().getDescricao() : produtoDTO.getDescricao());
        produto.get().setPreco(produtoDTO.getPreco() == null ?
                produto.get().getPreco() : produtoDTO.getPreco());

        produtoRepository.save(produto.get());

        return modelMapper.map(produto.get(), ProdutoDTO.class);
    }
}
