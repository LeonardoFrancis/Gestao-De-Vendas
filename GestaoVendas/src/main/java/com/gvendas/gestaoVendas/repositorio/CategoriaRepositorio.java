package com.gvendas.gestaoVendas.repositorio;

import com.gvendas.gestaoVendas.entidades.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepositorio extends JpaRepository<Categoria, Long> {
  
    Categoria findByNome(String nome); 
}
