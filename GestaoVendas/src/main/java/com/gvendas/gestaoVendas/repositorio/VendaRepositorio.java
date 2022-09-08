package com.gvendas.gestaoVendas.repositorio;

import com.gvendas.gestaoVendas.entidades.Venda;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VendaRepositorio extends JpaRepository<Venda, Long>{
 
    List<Venda> findByClienteCodigo(Long codigoCliente);
}
