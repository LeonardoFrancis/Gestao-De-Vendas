package com.gvendas.gestaoVendas.controlador;

import com.gvendas.gestaoVendas.dto.categoria.CategoriaRequestDTO;
import com.gvendas.gestaoVendas.dto.categoria.CategoriaResponseDTO;
import com.gvendas.gestaoVendas.entidades.Categoria;
import com.gvendas.gestaoVendas.servico.CategoriaServico;
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

@Api(tags = "Categoria")
@RestController
@RequestMapping("/categoria")
public class CategoriaControlador {

    @Autowired
    private CategoriaServico categoriaServico;

    @ApiOperation(value = "Listar", nickname = "listarTodas")
    @GetMapping
    public List<CategoriaResponseDTO> listarTodas() {
        return categoriaServico.listar().stream().map(categoria -> CategoriaResponseDTO.converterParaCategoriaDTO(categoria))
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "Listar por código", nickname = "listarPorCodigo")
    @GetMapping("/{codigo}")
    public ResponseEntity<CategoriaResponseDTO> listarPorCodigo(@PathVariable Long codigo) {
        Optional<Categoria> categoria = categoriaServico.listarPorCodigo(codigo);
        return categoria.isPresent()
                ? ResponseEntity.ok(CategoriaResponseDTO.converterParaCategoriaDTO(categoria.get()))
                : ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Salvar", nickname = "salvarCategoria")
    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> salvar(@Valid @RequestBody CategoriaRequestDTO categoriaDTO) {
        Categoria categoriaSalva = categoriaServico.salvar(categoriaDTO.converterParaEntidade());
        return ResponseEntity.status(HttpStatus.CREATED).body(CategoriaResponseDTO.converterParaCategoriaDTO(categoriaSalva));
    }

    @ApiOperation(value = "Atualizar", nickname = "atualizarCategoria")
    @PutMapping("/{codigo}")
    public ResponseEntity<CategoriaResponseDTO> atualizar(@PathVariable Long codigo, @Valid @RequestBody CategoriaRequestDTO categoriaDTO) {
        Categoria categoriaAtualizada = categoriaServico.atualizar(codigo, categoriaDTO.converterParaEntidade(codigo));
        return ResponseEntity.ok(CategoriaResponseDTO.converterParaCategoriaDTO(categoriaAtualizada));
    }

    @ApiOperation(value = "Deletar", nickname = "deletarCategoria")
    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long codigo) {
        categoriaServico.deletar(codigo);
    }
}
