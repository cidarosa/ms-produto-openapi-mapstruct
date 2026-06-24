package com.github.cidarosa.ms.produto.mapper;

import com.github.cidarosa.ms.produto.dto.ProdutoRequestDTO;
import com.github.cidarosa.ms.produto.dto.ProdutoResponseDTO;
import com.github.cidarosa.ms.produto.entities.Produto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    // pega categoriaId (DTO)
    //→ coloca dentro de categoria.id (Entity)
    @Mapping(source = "categoriaId", target = "categoria.id")
    Produto toEntity(ProdutoRequestDTO dto);

    ProdutoResponseDTO toResponse(Produto entity);

    List<ProdutoResponseDTO> toResponseList(List<Produto> produtos);

    void updateEntity(ProdutoRequestDTO dto, @MappingTarget Produto entity);
}
