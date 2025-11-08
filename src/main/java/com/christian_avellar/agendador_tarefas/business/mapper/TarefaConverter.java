package com.christian_avellar.agendador_tarefas.business.mapper;

import com.christian_avellar.agendador_tarefas.business.dto.TarefasDTO;
import com.christian_avellar.agendador_tarefas.infrastructure.entity.TarefasEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TarefaConverter {


    @Mapping(source = "id" , target = "id")
    @Mapping(source = "dataEvento" , target = "dataEvento")
    @Mapping(source = "dataCriacao" , target = "dataCriacao")
    TarefasEntity paraTarefasEntity(TarefasDTO dto);

    TarefasDTO paraTarefasDTO(TarefasEntity entity);

    List<TarefasEntity> paraListaTarefasEntity(List<TarefasDTO> dto);

    List<TarefasDTO> paraListaTarefasDTO(List<TarefasEntity> entity);



}
