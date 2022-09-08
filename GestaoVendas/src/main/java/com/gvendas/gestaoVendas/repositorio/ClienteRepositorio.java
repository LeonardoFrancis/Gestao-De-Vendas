package com.gvendas.gestaoVendas.repositorio;

import com.gvendas.gestaoVendas.entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {
  
    Cliente findByNome(String nome);
}
