package com.gvendas.gestaoVendas.controlador;

import com.gvendas.gestaoVendas.dto.cliente.ClienteRequestDTO;
import com.gvendas.gestaoVendas.dto.cliente.ClienteResponseDTO;
import com.gvendas.gestaoVendas.entidades.Cliente;
import com.gvendas.gestaoVendas.servico.ClienteServico;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Cliente")
@RestController
@RequestMapping("/cliente")
public class ClienteControlador {

    @Autowired
    private ClienteServico clienteServico;
    
    @ApiOperation(value = "Listar", nickname = "listarTodosClientes")
    @GetMapping
    public List<ClienteResponseDTO> listarTodas() {
        return clienteServico.listarTodos().stream().map(cliente -> ClienteResponseDTO.converterParaClienteDTO(cliente))
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "Listar por código", nickname = "listarClientePorCodigo")
    @GetMapping("/{codigo}")
    public ResponseEntity<ClienteResponseDTO> listarPorCodigo(@PathVariable Long codigo) {
        Optional<Cliente> cliente = clienteServico.listarPorCodigo(codigo);
        return cliente.isPresent() ? ResponseEntity.ok(ClienteResponseDTO.converterParaClienteDTO(cliente.get())) 
                : ResponseEntity.notFound().build();
    }
    
    @ApiOperation(value = "Salvar", nickname = "salvarCliente")
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> salvar(@Valid @RequestBody ClienteRequestDTO clienteDTO) {
        Cliente clienteSalvo = clienteServico.salvar(clienteDTO.converterParaEntidade());
        return ResponseEntity.status(HttpStatus.CREATED).body(ClienteResponseDTO.converterParaClienteDTO(clienteSalvo));
    }
    
    @PutMapping("/{codigo}")
    public ResponseEntity<ClienteResponseDTO> atualizar(@PathVariable Long codigo, @Valid @RequestBody ClienteRequestDTO clienteDTO) {
        Cliente clienteAtualizado = clienteServico.atualizar(codigo, clienteDTO.converterParaEntidade(codigo));
        return ResponseEntity.ok(ClienteResponseDTO.converterParaClienteDTO(clienteAtualizado));
    }
    
    @ApiOperation(value = "Deletar", nickname = "deletarCliente")
    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long codigo) {
        clienteServico.deletar(codigo);
    }
    
}
