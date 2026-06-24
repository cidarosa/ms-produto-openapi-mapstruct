package com.github.cidarosa.ms.produto.services;

import com.github.cidarosa.ms.produto.dto.ProdutoRequestDTO;
import com.github.cidarosa.ms.produto.dto.ProdutoResponseDTO;
import com.github.cidarosa.ms.produto.entities.Produto;
import com.github.cidarosa.ms.produto.exceptions.DatabaseException;
import com.github.cidarosa.ms.produto.exceptions.ResourceNotFoundException;
import com.github.cidarosa.ms.produto.mapper.ProdutoMapper;
import com.github.cidarosa.ms.produto.repositories.CategoriaRepository;
import com.github.cidarosa.ms.produto.repositories.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoMapper mapper;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional(readOnly = true)
    public List<ProdutoResponseDTO> findAllProdutos() {

//        List<Produto> produtos = produtoRepository.findAll();
//        return produtos.stream().map(ProdutoResponseDTO::new).toList();
        return mapper.toResponseList(produtoRepository.findAll());
    }

    @Transactional(readOnly = true)
    public ProdutoResponseDTO findProdutoById(Long id) {

        Produto produto = produtoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado. ID: " + id)
        );

        return mapper.toResponse(produto);
    }

    @Transactional
    public ProdutoResponseDTO saveProduto(ProdutoRequestDTO produtoDTO) {

        try {
            Produto produto = mapper.toEntity(produtoDTO);
            produto.setCategoria(categoriaRepository.getReferenceById(produtoDTO.getCategoriaId()));
            produto = produtoRepository.save(produto);
            return mapper.toResponse(produto);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Não foi possível salvar Produto. Categoria inexistente" +
                    " (ID: " + produtoDTO.getCategoriaId() + ")");
        }
    }

    @Transactional
    public ProdutoResponseDTO updateProduto(Long id, ProdutoRequestDTO produtoDTO) {

        try {
            Produto produto = produtoRepository.getReferenceById(id);
            mapper.updateEntity(produtoDTO, produto);
            produto.setCategoria(categoriaRepository.getReferenceById(produtoDTO.getCategoriaId()));
            produto = produtoRepository.save(produto);
            return mapper.toResponse(produto);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado. ID: " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Não foi possível salvar Produto. Categoria inexistente" +
                    " (ID: " + produtoDTO.getCategoriaId() + ")");
        }
    }

    @Transactional
    public void deleteProdutoById(Long id) {

        if (!produtoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado. ID: " + id);
        }

        produtoRepository.deleteById(id);
    }

//    private void copyDtoToProduto(ProdutoResponseDTO produtoDTO, Produto produto) {
//
//        produto.setNome(produtoDTO.getNome());
//        produto.setDescricao(produtoDTO.getDescricao());
//        produto.setValor(produtoDTO.getValor());
//
//        // Objeto completo gerenciado
//        Categoria categoria = categoriaRepository
//                .getReferenceById(produtoDTO.getCategoria().getId());
//
//        produto.setCategoria(categoria);
//    }
}


