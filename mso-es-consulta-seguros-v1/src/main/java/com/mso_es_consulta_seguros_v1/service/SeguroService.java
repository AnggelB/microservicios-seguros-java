package com.mso_es_consulta_seguros_v1.service;

import com.mso_es_consulta_seguros_v1.entity.Seguro;
import com.mso_es_consulta_seguros_v1.repository.SeguroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeguroService {

    private final SeguroRepository seguroRepository;

    public SeguroService(SeguroRepository seguroRepository){
        this.seguroRepository = seguroRepository;
    }

    public List<Seguro> obtenerTodos(){
        return seguroRepository.findAll();
    }

    public Optional<Seguro> obtenerPorId(Long id){
        return seguroRepository.findById(id);
    }

}
