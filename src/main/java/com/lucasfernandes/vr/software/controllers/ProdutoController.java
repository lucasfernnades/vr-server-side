package com.lucasfernandes.vr.software.controllers;

import com.lucasfernandes.vr.software.dto.ProdutoDTO;
import com.lucasfernandes.vr.software.services.ProdutoServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoController {

    private final ProdutoServices produtoServices;

    public ProdutoController(ProdutoServices produtoServices) {
        this.produtoServices = produtoServices;
    }

    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> getAllProdutos() {
        return ResponseEntity.ok(produtoServices.getAllProdutos());
    }

    @PostMapping
    public ResponseEntity<ProdutoDTO> registrarProduto(@RequestBody ProdutoDTO produtoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoServices.registrarProduto(produtoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarProdutoById(@PathVariable("id") Long id) {
        produtoServices.deletarProdutoById(id);
        return ResponseEntity.ok("Produto excluido com sucesso!");
    }

    @PutMapping
    public ResponseEntity<ProdutoDTO> atualizarInfoCliente(@RequestBody ProdutoDTO produtoDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(produtoServices.atualizarInfoProduto(produtoDTO));
    }
}
