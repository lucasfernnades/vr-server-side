package com.lucasfernandes.vr.software.repositories;

import com.lucasfernandes.vr.software.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
