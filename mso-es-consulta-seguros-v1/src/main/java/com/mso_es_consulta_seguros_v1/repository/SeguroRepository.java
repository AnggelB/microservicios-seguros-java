package com.mso_es_consulta_seguros_v1.repository;

import com.mso_es_consulta_seguros_v1.entity.Requisitos;
import com.mso_es_consulta_seguros_v1.entity.Seguro;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;

@Repository
public class SeguroRepository {

    Map <Long, Seguro> segurosDB;

    public SeguroRepository(){

        segurosDB = new HashMap<>();

        //SEGURO 1
        //Construyendo el objeto Requisitos usando su Builder
        Requisitos reqVida = Requisitos.builder()
                        .edadMinima(18)
                        .edadMaxima(75)
                        .idGenero(List.of(1, 2))
                        .build();
        //Construyendo el Seguro usando builder, pasando los requisitos y la lista
        Seguro seguroVida = Seguro.builder()
                        .id(1L)
                        .nombre("VIDA")
                        .descripcion(" Protección económica para tu familia en caso de\n" +
                                "fallecimiento natural o accidental.")
                        .costoAnual(new BigDecimal("700"))
                        .beneficios(List.of("Beneficio económico para tu familia, que se incrementará\n" +
                                "en caso de fallecimiento accidental.", "Servicio funerario sin costo adicional."))
                        .requisitos(reqVida)
                        .build();

        //Guardando en la bd (mapa)
        segurosDB.put(1L,seguroVida);

        //SEGURO 2
        Requisitos reqInfarto = Requisitos.builder()
                .edadMinima(15)
                .edadMaxima(64)
                .idGenero(List.of(1,2))
                .build();

        Seguro seguroInfarto = Seguro.builder()
                .id(2L)
                .nombre("INFARTO")
                .descripcion(" Protección económica de $50,000 por la primera\n" +
                        "ocurrencia de infarto al miocardio.")
                .costoAnual(new BigDecimal("400"))
                .beneficios(List.of("Envío de ambulancia.","Consultas médicas telefónicas ilimitadas", "No se requiere presentar exámenes médicos."))
                .requisitos(reqInfarto)
                .build();

        segurosDB.put(2L, seguroInfarto);

        //SEGURO 3
        Requisitos reqMujer = Requisitos.builder()
                .edadMinima(15)
                .edadMaxima(64)
                .idGenero(List.of(2))
                .build();

        Seguro seguroMujer = Seguro.builder()
                .id(3L)
                .nombre("MUJER")
                .descripcion("Protección económica de $50,000 por el primer\n" +
                        "diagnóstico de cáncer de mama o cervicouterino.")
                .costoAnual(new BigDecimal("300"))
                .beneficios(List.of("20 consultas psicológicas a domicilio (aplican solo si se\n" +
                        "diagnostica el cáncer).","Consultas psicológicas por teléfono ilimitadas.", "No se requiere presentar exámenes médicos."))
                .requisitos(reqMujer)
                .build();

        segurosDB.put(3L,seguroMujer);

    }

    public List<Seguro> findAll(){
        return new ArrayList<>(segurosDB.values());
    }

    public Optional<Seguro> findById(Long id){
        return Optional.ofNullable(segurosDB.get(id));
    }
}
