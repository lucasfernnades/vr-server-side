package com.lucasfernandes.vr.software.dto;

import com.lucasfernandes.vr.software.entities.Pedido;
import com.lucasfernandes.vr.software.entities.Produto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoProdutoDTO {

    private Long id;

    private Pedido pedido;

    private Produto produto;

    private Integer quantidade;
}
