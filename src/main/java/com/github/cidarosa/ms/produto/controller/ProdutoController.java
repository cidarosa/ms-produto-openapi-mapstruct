package com.github.cidarosa.ms.produto.controller;

import com.github.cidarosa.ms.produto.dto.ProdutoRequestDTO;
import com.github.cidarosa.ms.produto.dto.ProdutoResponseDTO;
import com.github.cidarosa.ms.produto.services.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
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
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    // forçando erro 500 para testes
    @Profile("test")
    @GetMapping("/--demo/500")
    public String force500() {
        throw new RuntimeException("Erro 500 forçado para demonstração");
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> getAllProdutos() {

        List<ProdutoResponseDTO> list = produtoService.findAllProdutos();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> getProdutoById(@PathVariable Long id) {

        ProdutoResponseDTO produtoDTO = produtoService.findProdutoById(id);

        return ResponseEntity.ok(produtoDTO);

    }

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> createProduto(@RequestBody @Valid ProdutoRequestDTO produtoRequestDTO) {

        ProdutoResponseDTO produtoDTO = produtoService.saveProduto(produtoRequestDTO);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(produtoDTO.getId())
                .toUri();

        return ResponseEntity.created(uri).body(produtoDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> updateProduto(@PathVariable Long id,
                                                            @RequestBody @Valid ProdutoRequestDTO produtoRequestDTO) {

        ProdutoResponseDTO produtoDTO = produtoService.updateProduto(id, produtoRequestDTO);

        return ResponseEntity.ok(produtoDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {

        produtoService.deleteProdutoById(id);

        return ResponseEntity.noContent().build();
    }


}











