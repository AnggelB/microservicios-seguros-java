package com.mso_es_consulta_seguros_v1.controller;

import com.mso_es_consulta_seguros_v1.dto.SegurosResponseDTO;
import com.mso_es_consulta_seguros_v1.entity.Seguro;
import com.mso_es_consulta_seguros_v1.exception.SeguroNoEncontradoException;
import com.mso_es_consulta_seguros_v1.service.SeguroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/seguros")
//Anotacion para crear automaticamente el constructor, busca atributos con la palabra final
@RequiredArgsConstructor
public class SeguroController {

    private final SeguroService seguroService;

    @GetMapping({"", "/{id}"})
    public ResponseEntity<?> consultarSeguros(@PathVariable(required = false) Long id){
        if(id!=null){
            Optional<Seguro> seguroOpt = seguroService.obtenerPorId(id);
            //Si esta vacio devolver 404
            if(seguroService.obtenerPorId(id).isEmpty()){
                throw new SeguroNoEncontradoException("No se encontro informacion asociada con el identificador ingresado. Asegurate que sea correcto");
            }else{
                return ResponseEntity.ok(seguroOpt.get());
            }
        }else{
            List<Seguro> seguroList = seguroService.obtenerTodos();
            SegurosResponseDTO segurosResponseDTO = new SegurosResponseDTO();
            segurosResponseDTO.setSeguros(seguroList);
            return ResponseEntity.ok(segurosResponseDTO);
        }
    }

}
