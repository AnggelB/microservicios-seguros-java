package com.mso_ts_validaciones_v1.service;

import com.mso_ts_validaciones_v1.dto.ClienteDTO;
import com.mso_ts_validaciones_v1.dto.RequisitosDTO;
import com.mso_ts_validaciones_v1.dto.SeguroInfoDTO;
import com.mso_ts_validaciones_v1.dto.ValidacionRequestDTO;
import com.mso_ts_validaciones_v1.dto.ValidacionResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ValidacionServiceTest {

    // Simulamos el RestTemplate para que no haga llamadas reales a internet
    @Mock
    private RestTemplate restTemplate;

    // Inyectamos el RestTemplate simulado en nuestro servicio real
    @InjectMocks
    private ValidacionService validacionService;

    @Test
    void debeRetornar201_cuandoClienteEsApto() {
        // 1. PREPARACIÓN (Arrange)
        // Configuracion de lo que respondería el microservicio falso
        RequisitosDTO requisitos = new RequisitosDTO();
        requisitos.setEdadMinima(18);
        requisitos.setEdadMaxima(75);
        requisitos.setIdGenero(Arrays.asList(1, 2)); // Ambos géneros

        SeguroInfoDTO seguroSimulado = new SeguroInfoDTO();
        seguroSimulado.setId(1L);
        seguroSimulado.setNombre("VIDA");
        seguroSimulado.setRequisitos(requisitos);

        // Mockito: cuando el servicio use restTemplate.getForObject, devuelve la simulación
        when(restTemplate.getForObject(anyString(), eq(SeguroInfoDTO.class))).thenReturn(seguroSimulado);

        // Preparando los datos del usuario (Request) con una fecha de nacimiento válida (36 años)
        ClienteDTO cliente = new ClienteDTO();
        cliente.setApellidoPaterno("Perez");
        cliente.setFechaNacimiento("25/02/1990");
        cliente.setIdGenero(1);

        ValidacionRequestDTO request = new ValidacionRequestDTO();
        request.setIdSeguro(1L);
        request.setCliente(cliente);

        // 2. EJECUCIÓN (Act)
        ValidacionResponseDTO response = validacionService.validar(request);

        // 3. VERIFICACIÓN (Assert)
        // Verificamos que el código devuelto sea 201 (Éxito)
        assertEquals(201, response.getCodigo());
    }

    @Test
    void debeRetornar401_cuandoClienteEsMenorDeEdad() {
        // 1. PREPARACIÓN (Arrange)
        RequisitosDTO requisitos = new RequisitosDTO();
        requisitos.setEdadMinima(18);
        requisitos.setEdadMaxima(75);

        SeguroInfoDTO seguroSimulado = new SeguroInfoDTO();
        seguroSimulado.setId(1L);
        seguroSimulado.setNombre("VIDA");
        seguroSimulado.setRequisitos(requisitos);

        when(restTemplate.getForObject(anyString(), eq(SeguroInfoDTO.class))).thenReturn(seguroSimulado);

        // Cliente que nació en 2025 (no cumple la edad mínima)
        ClienteDTO cliente = new ClienteDTO();
        cliente.setApellidoPaterno("Ramos");
        cliente.setFechaNacimiento("25/02/2025");
        cliente.setIdGenero(1);

        ValidacionRequestDTO request = new ValidacionRequestDTO();
        request.setIdSeguro(1L);
        request.setCliente(cliente);

        // 2. EJECUCIÓN (Act)
        ValidacionResponseDTO response = validacionService.validar(request);

        // 3. VERIFICACIÓN (Assert)
        // Verificando que el código devuelto sea 401 (Rechazado)
        assertEquals(401, response.getCodigo());
    }
}