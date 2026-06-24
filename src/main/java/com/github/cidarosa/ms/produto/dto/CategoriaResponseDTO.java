package com.github.cidarosa.ms.produto.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoriaResponseDTO {

    private Long id;

    private String nome;

//    public CategoriaResponseDTO(Categoria categoria) {
//        id = categoria.getId();
//        nome = categoria.getNome();
//    }
}
