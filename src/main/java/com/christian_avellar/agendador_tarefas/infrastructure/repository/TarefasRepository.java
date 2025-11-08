package com.christian_avellar.agendador_tarefas.infrastructure.repository;


import com.christian_avellar.agendador_tarefas.infrastructure.entity.TarefasEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TarefasRepository extends MongoRepository<TarefasEntity, String> {

    List<TarefasEntity> findByDataEventoBetween(LocalDateTime dataInicio, LocalDateTime dataFim);

    List<TarefasEntity> findByEmailUsuario(String email);
}
