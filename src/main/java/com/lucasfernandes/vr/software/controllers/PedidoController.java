package com.lucasfernandes.vr.software.controllers;

import com.lucasfernandes.vr.software.dto.PedidoDTO;
import com.lucasfernandes.vr.software.services.PedidoServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

    private final PedidoServices pedidoServices;

    public PedidoController(PedidoServices pedidoServices) {
        this.pedidoServices = pedidoServices;
    }

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> getAllPedidos() {
        return ResponseEntity.ok(pedidoServices.getAllPedidos());
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> registrarPedido(@RequestBody PedidoDTO pedidoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoServices.registrarPedido(pedidoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarPedidoById(@PathVariable("id") Long id) {
        pedidoServices.deletarPedidoById(id);
        return ResponseEntity.ok("Pedido excluido com sucesso!");
    }

    @PutMapping
    public ResponseEntity<PedidoDTO> atualizarInfoCliente(@RequestBody PedidoDTO pedidoDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(pedidoServices.atualizarInfoPedido(pedidoDTO));
    }

}
