package com.lucasfernandes.vr.software.repositories;

import com.lucasfernandes.vr.software.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
