package com.mso_es_consulta_seguros_v1.exception;

import com.mso_es_consulta_seguros_v1.dto.ErrorResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    //Manejador para cuando el ID no existe en el mapa: Error 404
    @ExceptionHandler(SeguroNoEncontradoException.class)
    public ResponseEntity<ErrorResponseDTO> manejarSeguroNoEncontrado(SeguroNoEncontradoException ex){
        //Armar la respuesta con codigo 404 y mensaje que trae la excepcion
        ErrorResponseDTO error = new ErrorResponseDTO(404, ex.getMessage());
        return ResponseEntity.status(404).body(error);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDTO> manejarIdentificadorInvalido(MethodArgumentTypeMismatchException ex){
        //Armar dto con codigo 400 y mensaje personalizado
        ErrorResponseDTO error = new ErrorResponseDTO(400, "El identificador ingresado no es valido. Por favor, verifica e intenta nuevamente");
        return ResponseEntity.status(400).body(error);
    }

}
