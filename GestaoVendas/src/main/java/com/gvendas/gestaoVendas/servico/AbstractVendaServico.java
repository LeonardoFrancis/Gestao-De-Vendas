package com.gvendas.gestaoVendas.servico;

import com.gvendas.gestaoVendas.dto.venda.ClienteVendaResponseDTO;
import com.gvendas.gestaoVendas.dto.venda.ItemVendaRequestDTO;
import com.gvendas.gestaoVendas.dto.venda.ItemVendaResponseDTO;
import com.gvendas.gestaoVendas.dto.venda.VendaResponseDTO;
import com.gvendas.gestaoVendas.entidades.ItemVenda;
import com.gvendas.gestaoVendas.entidades.Produto;
import com.gvendas.gestaoVendas.entidades.Venda;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public abstract class AbstractVendaServico {

    protected VendaResponseDTO criandoVendaResponseDTO(Venda venda, List<ItemVenda> itensVendaList) {
        List<ItemVendaResponseDTO> itensVendaResponseDto = itensVendaList.stream()
                .map(this::criandoItensVendaResponseDTO).collect(Collectors.toList());
        return new VendaResponseDTO(venda.getCodigo(), venda.getData(), itensVendaResponseDto);
    } 
    
    protected ItemVendaResponseDTO criandoItensVendaResponseDTO(ItemVenda itemVenda) {
        return new ItemVendaResponseDTO(itemVenda.getCodigo(), itemVenda.getQuantidade(), itemVenda.getPrecoVendido(), itemVenda.getProduto().getCodigo(), itemVenda.getProduto().getDescricao());
    }
    
    protected ClienteVendaResponseDTO retornandoClienteVendaResponseDto(Venda venda, List<ItemVenda> itensVendaList) {
        return new ClienteVendaResponseDTO(venda.getCliente().getNome(), Arrays
                .asList(criandoVendaResponseDTO(venda, itensVendaList)));
    }
    
    protected ItemVenda criandoItemVenda(ItemVendaRequestDTO itemVendaDto, Venda venda) {
        return new ItemVenda(itemVendaDto.getQuantidade(), itemVendaDto.getPrecoVendido(), new Produto(itemVendaDto.getCodigoProduto()), venda);
    }
}
