package com.lucasfernandes.vr.software.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {

    private Long id;

    private String nome;

    private Float limiteCompra;

    private Integer fechamentoFatura;
}
