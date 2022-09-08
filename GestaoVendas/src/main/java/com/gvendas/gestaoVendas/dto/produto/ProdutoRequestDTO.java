package com.gvendas.gestaoVendas.dto.produto;

import com.gvendas.gestaoVendas.entidades.Categoria;
import com.gvendas.gestaoVendas.entidades.Produto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@ApiModel(value = "Produto requisição DTO")
public class ProdutoRequestDTO {

    @ApiModelProperty(value = "Descrição")
    @NotBlank(message = "Descrição")
    @Length(min = 3, max = 100, message = "Descrição")
    private String descricao;
    
    @NotNull(message = "Quantidade")
    @ApiModelProperty(value = "Quantidade")
    private Integer quantidade;
    
    @NotNull(message = "Preço custo")
    @ApiModelProperty(value = "Preço custo")
    private BigDecimal precoCusto;
    
    @NotNull(message = "Preço venda")
    @ApiModelProperty(value = "Preço venda")
    private BigDecimal precoVenda;
    
    @Length(max = 100, message = "Observação")
    @ApiModelProperty(value = "Observação")
    private String observacao;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoCusto() {
        return precoCusto;
    }

    public void setPrecoCusto(BigDecimal precoCusto) {
        this.precoCusto = precoCusto;
    }

    public BigDecimal getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(BigDecimal precoVenda) {
        this.precoVenda = precoVenda;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
    
    public Produto converterParaEntidade(Long codigoCategoria) {
        return new Produto(descricao, quantidade, precoCusto, precoVenda, observacao, new Categoria(codigoCategoria));
    }
    
    public Produto converterParaEntidade(Long codigoCategoria, Long codigoProduto) {
        return new Produto(codigoProduto, descricao, quantidade, precoCusto, precoVenda, observacao, new Categoria(codigoCategoria));
    }
}
