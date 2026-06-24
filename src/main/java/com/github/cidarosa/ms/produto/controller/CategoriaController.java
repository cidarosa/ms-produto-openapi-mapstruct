package com.github.cidarosa.ms.produto.controller;

import com.github.cidarosa.ms.produto.dto.CategoriaRequestDTO;
import com.github.cidarosa.ms.produto.dto.CategoriaResponseDTO;
import com.github.cidarosa.ms.produto.services.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> getAllCategorias() {

        List<CategoriaResponseDTO> categorias = categoriaService.findAllCategoria();

        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> getCategoriaById(@PathVariable Long id) {

        CategoriaResponseDTO categoriaDTO = categoriaService.findCategoriaById(id);

        return ResponseEntity.ok(categoriaDTO);
    }

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> createCategoria(
            @RequestBody @Valid CategoriaRequestDTO categoriaRequestDTO) {

        CategoriaResponseDTO categoriaDTO = categoriaService.saveCategoria(categoriaRequestDTO);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(categoriaDTO.getId())
                .toUri();

        return ResponseEntity.created(uri).body(categoriaDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> updateCategoria(@PathVariable Long id,
                                                                @RequestBody @Valid CategoriaRequestDTO categoriaRequestDTO) {
        CategoriaResponseDTO categoriaDTO = categoriaService.updateCategoria(id, categoriaRequestDTO);

        return ResponseEntity.ok(categoriaDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoriaById(@PathVariable Long id) {

        categoriaService.deleteCategoriaById(id);

        return ResponseEntity.noContent().build();
    }

}
