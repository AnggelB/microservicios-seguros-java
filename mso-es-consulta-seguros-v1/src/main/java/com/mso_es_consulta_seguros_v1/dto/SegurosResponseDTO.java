package com.mso_es_consulta_seguros_v1.dto;

import com.mso_es_consulta_seguros_v1.entity.Seguro;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

//Clase para usar el patron DTO y formateando la respuesta
public class SegurosResponseDTO {
    private List<Seguro> seguros;
}
