package com.github.cidarosa.ms.produto.services;

import com.github.cidarosa.ms.produto.dto.CategoriaRequestDTO;
import com.github.cidarosa.ms.produto.dto.CategoriaResponseDTO;
import com.github.cidarosa.ms.produto.entities.Categoria;
import com.github.cidarosa.ms.produto.exceptions.DatabaseException;
import com.github.cidarosa.ms.produto.exceptions.ResourceNotFoundException;
import com.github.cidarosa.ms.produto.mapper.CategoriaMapper;
import com.github.cidarosa.ms.produto.repositories.CategoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CategoriaMapper mapper;

    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> findAllCategoria() {

//        List<Categoria> list = categoriaRepository.findAll();
//        return list.stream().map(CategoriaResponseDTO::new).toList();

        return mapper.toResponseList(categoriaRepository.findAll());
    }

    @Transactional(readOnly = true)
    public CategoriaResponseDTO findCategoriaById(Long id) {

        Categoria categoria = categoriaRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado")
        );

        return mapper.toResponse(categoria);
    }

    @Transactional
    public CategoriaResponseDTO saveCategoria(CategoriaRequestDTO inputDTO) {

        Categoria categoria = mapper.toEntity(inputDTO);
        categoria = categoriaRepository.save(categoria);
        return mapper.toResponse(categoria);
    }

    @Transactional
    public CategoriaResponseDTO updateCategoria(Long id, CategoriaRequestDTO inputDTO) {

        try {
            Categoria categoria = categoriaRepository.getReferenceById(id);
            mapper.updateEntity(inputDTO, categoria);
            categoria = categoriaRepository.save(categoria);
            return mapper.toResponse(categoria);
        } catch (EntityNotFoundException ex) {
            throw new ResourceNotFoundException("Recurso não encontrado. ID: " + id);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteCategoriaById(Long id) {

        if (!categoriaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado. ID: " + id);
        }

        try {
            categoriaRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Não foi possível excluir a categoria. " +
                    "Existem produtos associados a ela."
            );
        }
    }
}
