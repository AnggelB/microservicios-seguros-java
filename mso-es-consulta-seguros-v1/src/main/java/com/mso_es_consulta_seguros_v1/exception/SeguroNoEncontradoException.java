package com.mso_es_consulta_seguros_v1.exception;

public class SeguroNoEncontradoException extends RuntimeException{
    //Constructor que recibe el texto
    public SeguroNoEncontradoException(String mensaje){
        //Pasando el texto (mensaje) a la clase padre RuntimeException
        super(mensaje);
    }
}
