package com.gvendas.gestaoVendas.dto.cliente;

import com.gvendas.gestaoVendas.entidades.Cliente;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Cliente retorno DTO")
public class ClienteResponseDTO {
    
    @ApiModelProperty(value = "Código")
    private Long codigo;
    
    @ApiModelProperty(value = "Nome")
    private String nome;
    
    @ApiModelProperty(value = "Telefone")
    private String telefone;
    
    @ApiModelProperty(value = "Ativo")
    private Boolean ativo;

    private EnderecoResponseDTO enderecoDTO;

    public ClienteResponseDTO(Long codigo, String nome, String telefone, Boolean ativo, EnderecoResponseDTO enderecoDTO) {
        this.codigo = codigo;
        this.nome = nome;
        this.telefone = telefone;
        this.ativo = ativo;
        this.enderecoDTO = enderecoDTO;
    }
    
    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

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

    public EnderecoResponseDTO getEnderecoDTO() {
        return enderecoDTO;
    }

    public void setEnderecoDTO(EnderecoResponseDTO enderecoDTO) {
        this.enderecoDTO = enderecoDTO;
    }
    
    public static ClienteResponseDTO converterParaClienteDTO(Cliente cliente) {
        EnderecoResponseDTO enderecoResponseDTO = new EnderecoResponseDTO(cliente.getEndereco().getLogradouro(), cliente.getEndereco().getNumero(), cliente.getEndereco().getComplemento(), 
                cliente.getEndereco().getBairro(), cliente.getEndereco().getCep(), cliente.getEndereco().getCidade(), cliente.getEndereco().getEstado());
        return new ClienteResponseDTO(cliente.getCodigo(), cliente.getNome(), cliente.getTelefone(), cliente.getAtivo(), enderecoResponseDTO);
    }

}
