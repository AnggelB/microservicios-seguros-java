package com.mso_es_consulta_seguros_v1.entity;

import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Requisitos {

    private Integer edadMinima;
    private Integer edadMaxima;
    private List<Integer> idGenero;

}
