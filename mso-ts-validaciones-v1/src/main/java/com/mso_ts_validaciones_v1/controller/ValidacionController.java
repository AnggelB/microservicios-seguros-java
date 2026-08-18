package com.mso_ts_validaciones_v1.controller;

import com.mso_ts_validaciones_v1.dto.ValidacionRequestDTO;
import com.mso_ts_validaciones_v1.dto.ValidacionResponseDTO;
import com.mso_ts_validaciones_v1.service.ValidacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/validaciones")
@RequiredArgsConstructor
public class ValidacionController {

    private final ValidacionService validacionService;

    @PostMapping
    public ResponseEntity<ValidacionResponseDTO> validarCliente(@RequestBody ValidacionRequestDTO request) {
        ValidacionResponseDTO response = validacionService.validar(request);

        if (response.getCodigo() == 201) {
            // Devuelve Estatus HTTP 200, pero el JSON adentro lleva "codigo": 201
            return ResponseEntity.status(200).body(response);
        } else if (response.getCodigo() == 401) {
            // Devuelve Estatus HTTP 401 y el JSON adentro lleva "codigo": 401
            return ResponseEntity.status(401).body(response);
        } else {
            // Para cualquier otro error (como formato de fecha mal o seguro inexistente)
            return ResponseEntity.status(400).body(response);
        }
    }
}