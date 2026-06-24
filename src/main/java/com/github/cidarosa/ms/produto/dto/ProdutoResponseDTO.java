package com.github.cidarosa.ms.produto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
//@NoArgsConstructor
@Getter
//@Setter
@Builder
public class ProdutoResponseDTO {

    private final Long id;
    private final  String nome;
    private final  String descricao;
    private final  Double valor;
    private final  CategoriaResponseDTO categoria;
}
