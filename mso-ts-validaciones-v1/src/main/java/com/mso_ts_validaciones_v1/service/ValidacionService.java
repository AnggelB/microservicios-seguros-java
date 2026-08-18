package com.mso_ts_validaciones_v1.service;

import com.mso_ts_validaciones_v1.dto.ValidacionRequestDTO;
import com.mso_ts_validaciones_v1.dto.ValidacionResponseDTO;
import com.mso_ts_validaciones_v1.dto.SeguroInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class ValidacionService {

    private final RestTemplate restTemplate;

    public ValidacionResponseDTO validar(ValidacionRequestDTO request) {

        //Consultando el primer microservicio para obtener el nombre y los requisitos
        Long idSeguro = request.getIdSeguro();
        String urlPrimerMicroservicio = "http://ms-seguros:8080/seguros/" + idSeguro;
        SeguroInfoDTO seguroInfo;

        try {
            seguroInfo = restTemplate.getForObject(urlPrimerMicroservicio, SeguroInfoDTO.class);
        } catch (Exception e) {
            return new ValidacionResponseDTO(404, "El seguro no existe.");
        }

        //Preparando los mensajes
        String nombreSeguro = seguroInfo.getNombre();
        String msjFallo = "Lamentamos informarle que no podemos ofrecerle el seguro " + nombreSeguro + " porque no cumple con ciertos requisitos establecidos. Por favor, revise los criterios necesarios o considere otras opciones";
        String msjExito = "Nos complace informarle que cumple con los requisitos para acceder al seguro " + nombreSeguro + " ¡Felicitaciones!";

        //Validacion: que al menos tenga un apellido
        String apPat = request.getCliente().getApellidoPaterno();
        String apMat = request.getCliente().getApellidoMaterno();
        if ((apPat == null || apPat.trim().isEmpty()) && (apMat == null || apMat.trim().isEmpty())) {
            return new ValidacionResponseDTO(401, msjFallo);
        }

        //Calculando la edad
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaNac;
        try {
            fechaNac = LocalDate.parse(request.getCliente().getFechaNacimiento(), formatter);
        } catch (Exception e) {
            return new ValidacionResponseDTO(400, "Formato de fecha inválido.");
        }
        int edad = Period.between(fechaNac, LocalDate.now()).getYears();
        Integer idGeneroCliente = request.getCliente().getIdGenero();

        //Validando reglas de Negocio (Edad y Género)
        int edadMinima = seguroInfo.getRequisitos().getEdadMinima();
        int edadMaxima = seguroInfo.getRequisitos().getEdadMaxima();
        var generosPermitidos = seguroInfo.getRequisitos().getIdGenero();

        if (generosPermitidos != null && !generosPermitidos.contains(idGeneroCliente)) {
            return new ValidacionResponseDTO(401, msjFallo);
        }

        if (edad >= edadMinima && edad <= edadMaxima) {
            return new ValidacionResponseDTO(201, msjExito); // 201 en el JSON
        } else {
            return new ValidacionResponseDTO(401, msjFallo); // 401 si no cumple
        }
    }
}