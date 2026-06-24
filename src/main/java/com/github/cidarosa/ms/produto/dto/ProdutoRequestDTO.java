package com.github.cidarosa.ms.produto.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProdutoRequestDTO {

    @NotBlank(message = "Campo nome é requerido")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    @Schema(example = "Apache Kafka e Spring Boot")
    private String nome;

    @NotBlank(message = "Campo descrição é requerido")
    @Size(min = 10, message = "O descrição deve ter no mínimo 10 caracteres")
    @Schema(example = "Apache Kafka e Spring Boot - Comunicação assíncrona entre microsserviços. E-book")
    private String descricao;

    @NotNull(message = "O campo valor é requerido")
    @Positive(message = "O campo valor de ser um número positivo maior que zero")
    @Schema(example = "39.90")
    private Double valor;

    @NotNull(message = "Campo Id categoria é requerido")
    @Schema(example = "3")
    private Long categoriaId;

}
