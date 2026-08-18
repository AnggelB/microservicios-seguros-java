package com.mso_ts_validaciones_v1.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeguroInfoDTO {
    private Long id;
    private String nombre;
    private RequisitosDTO requisitos;
}