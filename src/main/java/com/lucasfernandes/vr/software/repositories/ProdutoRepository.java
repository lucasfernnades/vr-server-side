package com.lucasfernandes.vr.software.repositories;

import com.lucasfernandes.vr.software.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
