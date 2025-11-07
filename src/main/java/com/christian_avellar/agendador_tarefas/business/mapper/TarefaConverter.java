package com.christian_avellar.agendador_tarefas.business.mapper;

import com.christian_avellar.agendador_tarefas.business.dto.TarefasDTO;
import com.christian_avellar.agendador_tarefas.infrastructure.entity.TarefasEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TarefaConverter {

    TarefasEntity paraTarefasEntity(TarefasDTO dto);
    TarefasDTO paraTarefasDTO(TarefasEntity entity);
}
