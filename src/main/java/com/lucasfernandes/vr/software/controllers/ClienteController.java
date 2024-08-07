package com.lucasfernandes.vr.software.controllers;

import com.lucasfernandes.vr.software.dto.ClienteDTO;
import com.lucasfernandes.vr.software.services.ClienteServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {

    private final ClienteServices clienteServices;

    @Autowired
    public ClienteController(ClienteServices clienteServices) {
        this.clienteServices = clienteServices;
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> getAllClientes() {
        return ResponseEntity.ok(clienteServices.getAllClientes());
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> registrarCliente(@RequestBody ClienteDTO clienteDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteServices.registrarCliente(clienteDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarClienteById(@PathVariable("id") Long id) {
        clienteServices.deletarClienteById(id);
        return ResponseEntity.ok("Cliente excluido com sucesso!");
    }

    @PutMapping
    public ResponseEntity<ClienteDTO> atualizarInfoCliente(@RequestBody ClienteDTO clienteDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(clienteServices.atulizarInfoCliente(clienteDTO));
    }
}
