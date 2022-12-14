package com.gvendas.gestaoVendas.dto.cliente;

import com.gvendas.gestaoVendas.entidades.Cliente;
import com.gvendas.gestaoVendas.entidades.Endereco;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

@ApiModel("Cliente Requisição DTO")
public class ClienteRequestDTO {

    @ApiModelProperty(value = "Nome")
    @NotBlank(message = "Nome")
    @Length(min = 3, max = 50, message = "Nome")
    private String nome;
    
    @ApiModelProperty(value = "Telefone")
    @NotBlank(message = "Telefone")
    @Pattern(regexp = "\\([\\d]{2}\\)[\\d]{5}[- .][\\d]{4}" , message = "Telefone")
    private String telefone;
    
    @ApiModelProperty(value = "Ativo")
    @NotNull(message = "Ativo")
    private Boolean ativo;
    
    @ApiModelProperty(value = "Endereço")
    @NotNull(message = "Endereço")
    @Valid
    private EnderecoRequestDTO enderecoDTO;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public EnderecoRequestDTO getEnderecoDTO() {
        return enderecoDTO;
    }

    public void setEnderecoDTO(EnderecoRequestDTO enderecoDTO) {
        this.enderecoDTO = enderecoDTO;
    }
    
    public Cliente converterParaEntidade() {
        Endereco endereco = new Endereco(enderecoDTO.getLogradouro(), enderecoDTO.getNumero(), enderecoDTO.getComplemento(), enderecoDTO.getBairro(), 
                enderecoDTO.getCep(), enderecoDTO.getCidade(), enderecoDTO.getEstado());
        return new Cliente(nome, telefone, ativo, endereco);
    }
    
    public Cliente converterParaEntidade(Long codigo) {
        Endereco endereco = new Endereco(enderecoDTO.getLogradouro(), enderecoDTO.getNumero(), enderecoDTO.getComplemento(), enderecoDTO.getBairro(), 
                enderecoDTO.getCep(), enderecoDTO.getCidade(), enderecoDTO.getEstado());
        return new Cliente(codigo, nome, telefone, ativo, endereco);
    }
}
