package com.mso_es_consulta_seguros_v1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ErrorResponseDTO {
    private int codigo;
    private String respuesta;
}
