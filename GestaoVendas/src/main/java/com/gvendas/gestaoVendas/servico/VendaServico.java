package com.gvendas.gestaoVendas.servico;

import com.gvendas.gestaoVendas.dto.venda.ClienteVendaResponseDTO;
import com.gvendas.gestaoVendas.dto.venda.ItemVendaRequestDTO;
import com.gvendas.gestaoVendas.dto.venda.VendaRequestDTO;
import com.gvendas.gestaoVendas.dto.venda.VendaResponseDTO;
import com.gvendas.gestaoVendas.entidades.Cliente;
import com.gvendas.gestaoVendas.entidades.ItemVenda;
import com.gvendas.gestaoVendas.entidades.Produto;
import com.gvendas.gestaoVendas.entidades.Venda;
import com.gvendas.gestaoVendas.excecao.RegraNegocioException;
import com.gvendas.gestaoVendas.repositorio.ItemVendaRepositorio;
import com.gvendas.gestaoVendas.repositorio.VendaRepositorio;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VendaServico extends AbstractVendaServico {

    private VendaRepositorio vendaRepositorio;
    private ItemVendaRepositorio itemVendaRepositorio;
    private ClienteServico clienteServico;
    private ProdutoServico produtoServico;

    @Autowired
    public VendaServico(VendaRepositorio vendaRepositorio, ItemVendaRepositorio itemVendaRepositorio, ClienteServico clienteServico, ProdutoServico produtoServico) {
        this.vendaRepositorio = vendaRepositorio;
        this.itemVendaRepositorio = itemVendaRepositorio;
        this.clienteServico = clienteServico;
        this.produtoServico = produtoServico;
    }
    
    public ClienteVendaResponseDTO listaVendaPorCliente(Long codigoCliente) {
        Cliente cliente = validarClienteVendaExiste(codigoCliente);
        List<VendaResponseDTO> vendaResponseDtoList = vendaRepositorio.findByClienteCodigo(codigoCliente).stream()
                .map(venda -> criandoVendaResponseDTO(venda, itemVendaRepositorio.findByVendaCodigo(venda.getCodigo()))).collect(Collectors.toList());
        return new ClienteVendaResponseDTO(cliente.getNome(), vendaResponseDtoList);
    }
    
    public ClienteVendaResponseDTO listarVendaPorCodigo(Long codigoVenda) {
        Venda venda = validarVendaExiste(codigoVenda);
        List<ItemVenda> itensVendaList = itemVendaRepositorio.findByVendaCodigo(venda.getCodigo());
        return retornandoClienteVendaResponseDto(venda, itensVendaList);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
    public ClienteVendaResponseDTO salvar(Long codigoCliente, VendaRequestDTO vendaDto) {
        Cliente cliente = validarClienteVendaExiste(codigoCliente);
        validarProdutoExisteEAtualizarQuantidade(vendaDto.getItensVendaDTO());
        Venda vendaSalva = salvarVenda(cliente, vendaDto);
        return retornandoClienteVendaResponseDto(vendaSalva, itemVendaRepositorio.findByVendaCodigo(vendaSalva.getCodigo()));
    }
    
    public ClienteVendaResponseDTO atualizar(Long codigoVenda, Long codigoCliente, VendaRequestDTO vendaDto){
        validarVendaExiste(codigoVenda);
        Cliente cliente = validarClienteVendaExiste(codigoCliente);
        List<ItemVenda> itensVendaList = itemVendaRepositorio.findByVendaCodigo(codigoVenda);
        validarProdutoExisteDevolverEstoque(itensVendaList);
        validarProdutoExisteEAtualizarQuantidade(vendaDto.getItensVendaDTO());
        itemVendaRepositorio.deleteAll(itensVendaList);
        Venda vendaAtualizada = atualizarVenda(codigoVenda, cliente, vendaDto);
        return retornandoClienteVendaResponseDto(vendaAtualizada, itemVendaRepositorio.findByVendaCodigo(vendaAtualizada.getCodigo()));
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
    public void deletar(Long codigoVenda) {
        validarVendaExiste(codigoVenda);
        List<ItemVenda> itensVenda = itemVendaRepositorio.findByVendaCodigo(codigoVenda);
        validarProdutoExisteDevolverEstoque(itensVenda);
        itemVendaRepositorio.deleteAll(itensVenda);
        vendaRepositorio.deleteById(codigoVenda);
    }
    
    private void validarProdutoExisteDevolverEstoque(List<ItemVenda> itensVenda) {
        itensVenda.forEach(item -> {
            Produto produto = produtoServico.validarProdutoExiste(item.getProduto().getCodigo());
            produto.setQuantidade(produto.getQuantidade() + item.getQuantidade());
            produtoServico.atualizarQuantidadeEmEstoque(produto);
        });
    }
    
    private Venda salvarVenda(Cliente cliente, VendaRequestDTO vendaDto) {
        Venda vendaSalva = vendaRepositorio.save(new Venda(vendaDto.getData(), cliente));
        vendaDto.getItensVendaDTO().stream().map(itemVendaDto -> criandoItemVenda(itemVendaDto, vendaSalva))
                .forEach(itemVendaRepositorio::save);
        return vendaSalva;
    }
    
    private Venda atualizarVenda(Long codigoVenda, Cliente cliente, VendaRequestDTO vendaDto) {
        Venda vendaSalva = vendaRepositorio.save(new Venda(codigoVenda, vendaDto.getData(), cliente));
        vendaDto.getItensVendaDTO().stream().map(itemVendaDto -> criandoItemVenda(itemVendaDto, vendaSalva))
                .forEach(itemVendaRepositorio::save);
        return vendaSalva;
    }
    
    private void validarProdutoExisteEAtualizarQuantidade (List<ItemVendaRequestDTO> itensVendaDto) {
        itensVendaDto.forEach(item -> {
            Produto produto = produtoServico.validarProdutoExiste(item.getCodigoProduto());
            validarQuantidadeDoProdutoExiste(produto, item.getQuantidade());
            produto.setQuantidade(produto.getQuantidade() - item.getQuantidade());
            produtoServico.atualizarQuantidadeEmEstoque(produto);
        });
    }
    
    private void validarQuantidadeDoProdutoExiste(Produto produto, Integer qtdeVendaDto) {
        if(!(produto.getQuantidade() >= qtdeVendaDto)) {
            throw new RegraNegocioException(String.format("A quantidade %s informada para o produto %s não está disponível em estoque", 
                    qtdeVendaDto, produto.getDescricao()));
        }
    }
    
    private Venda validarVendaExiste(Long codigoVenda) {
        Optional<Venda> venda = vendaRepositorio.findById(codigoVenda);
        if (venda.isEmpty()) {
            throw new RegraNegocioException(String.format("Venda de código %s não encontrada", codigoVenda));
        }
        return venda.get();
    }

    private Cliente validarClienteVendaExiste(Long codigoCliente) {
        Optional<Cliente> cliente = clienteServico.listarPorCodigo(codigoCliente);
        if (cliente.isEmpty()) {
            throw new RegraNegocioException(String.format("O cliente de código %s informado não existe no cadastro", codigoCliente));
        }
        return cliente.get();
    }
}
