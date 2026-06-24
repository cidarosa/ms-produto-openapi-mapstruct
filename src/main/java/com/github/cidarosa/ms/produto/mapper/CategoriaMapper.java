package com.github.cidarosa.ms.produto.mapper;

import com.github.cidarosa.ms.produto.dto.CategoriaRequestDTO;
import com.github.cidarosa.ms.produto.dto.CategoriaResponseDTO;
import com.github.cidarosa.ms.produto.entities.Categoria;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {

        Categoria toEntity(CategoriaRequestDTO dto);

        CategoriaResponseDTO toResponse(Categoria entity);

        List<CategoriaResponseDTO> toResponseList(List<Categoria> categorias);

//        @MappingTarget → atualizar objeto existente
        void updateEntity(CategoriaRequestDTO dto, @MappingTarget Categoria entity);
}
