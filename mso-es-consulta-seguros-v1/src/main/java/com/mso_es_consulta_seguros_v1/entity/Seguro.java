package com.mso_es_consulta_seguros_v1.entity;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Seguro {

    private Long id;
    private String nombre;
    private String descripcion;
    private BigDecimal costoAnual;
    private List <String> beneficios;
    private Requisitos requisitos;

}

