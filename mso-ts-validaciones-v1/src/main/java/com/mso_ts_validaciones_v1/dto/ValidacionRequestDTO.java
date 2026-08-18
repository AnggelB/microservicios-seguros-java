package com.mso_ts_validaciones_v1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ValidacionRequestDTO {
    private Long idSeguro;
    private ClienteDTO cliente;
}