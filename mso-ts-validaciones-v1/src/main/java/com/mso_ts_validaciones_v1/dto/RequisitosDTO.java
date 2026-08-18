package com.mso_ts_validaciones_v1.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class RequisitosDTO {
    private int edadMinima;
    private int edadMaxima;
    private List<Integer> idGenero;
}