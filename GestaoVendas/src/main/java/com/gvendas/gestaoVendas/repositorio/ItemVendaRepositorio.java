package com.gvendas.gestaoVendas.repositorio;

import com.gvendas.gestaoVendas.entidades.ItemVenda;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItemVendaRepositorio extends JpaRepository<ItemVenda, Long> {
    
    @Query("Select new com.gvendas.gestaoVendas.entidades.ItemVenda("
            + " iv.codigo, iv.quantidade, iv.precoVendido, iv.produto, iv.venda)"
            + " from ItemVenda iv"
            + " where iv.venda.codigo = :codigoVenda")
    List<ItemVenda> findByVendaCodigo(Long codigoVenda);
}
