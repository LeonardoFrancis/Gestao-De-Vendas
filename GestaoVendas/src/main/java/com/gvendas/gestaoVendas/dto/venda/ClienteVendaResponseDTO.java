package com.gvendas.gestaoVendas.dto.venda;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

@ApiModel("Cliente da venda retorno DTO")
public class ClienteVendaResponseDTO {

    @ApiModelProperty(value = "Nome cliente")
    private String nome;
    
    @ApiModelProperty(value = "Venda")
    private List<VendaResponseDTO> vendaResponseDTO;

    public ClienteVendaResponseDTO(String nome, List<VendaResponseDTO> vendaResponseDTO) {
        this.nome = nome;
        this.vendaResponseDTO = vendaResponseDTO;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<VendaResponseDTO> getVendaResponseDTO() {
        return vendaResponseDTO;
    }

    public void setVendaResponseDTO(List<VendaResponseDTO> vendaResponseDTO) {
        this.vendaResponseDTO = vendaResponseDTO;
    }
}
