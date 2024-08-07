package com.lucasfernandes.vr.software.dto;

import com.lucasfernandes.vr.software.entities.Cliente;
import com.lucasfernandes.vr.software.entities.PedidoProduto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {

    private Long id;

    private Cliente cliente;

    private List<PedidoProduto> produtos;

    private Boolean status;
}
