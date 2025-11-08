package com.christian_avellar.agendador_tarefas.business;

import com.christian_avellar.agendador_tarefas.business.dto.TarefasDTO;
import com.christian_avellar.agendador_tarefas.business.mapper.TarefaConverter;
import com.christian_avellar.agendador_tarefas.business.mapper.TarefaUpdateConverter;
import com.christian_avellar.agendador_tarefas.infrastructure.entity.TarefasEntity;
import com.christian_avellar.agendador_tarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.christian_avellar.agendador_tarefas.infrastructure.exceptions.ResourceNotFound;
import com.christian_avellar.agendador_tarefas.infrastructure.repository.TarefasRepository;
import com.christian_avellar.agendador_tarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefaService {

    private final TarefasRepository tarefasRepository;
    private final TarefaConverter tarefaConverter;
    private final JwtUtil jwtUtil;
    private final TarefaUpdateConverter tarefaUpdateConverter;


    public TarefasDTO gravarTarefa(String token, TarefasDTO dto) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        dto.setDataCriacao(LocalDateTime.now());
        dto.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE);
        TarefasEntity entity = tarefaConverter.paraTarefasEntity(dto);
        dto.setEmailUsuario(email);
        return tarefaConverter.paraTarefasDTO(
                tarefasRepository.save(entity));
    }

    public List<TarefasDTO> buscaTarefasPorPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return tarefaConverter.paraListaTarefasDTO(
                tarefasRepository.findByDataEventoBetween(dataInicio, dataFim));

    }

    public List<TarefasDTO> buscaTarefasPorEmail(String token) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        List<TarefasEntity> listaTarefas = tarefasRepository.findByEmailUsuario(email);
        return tarefaConverter.paraListaTarefasDTO(listaTarefas);
    }

    public void deletarTarefaPorId(String id) {
        try {
            tarefasRepository.deleteById(id);
        } catch (ResourceNotFound e) {
            throw new ResourceNotFound("Erro ao deletar tarefa" + id, e.getCause());

        }
    }

    public TarefasDTO alteraStatusTarefa(StatusNotificacaoEnum status, String id) {
        try {
            TarefasEntity entity = tarefasRepository.findById(id).
                    orElseThrow(() -> new ResourceNotFound("Tarefa não encontrada" + id));
            entity.setStatusNotificacaoEnum(status);
            return tarefaConverter.paraTarefasDTO(tarefasRepository.save(entity));
        } catch (ResourceNotFound e) {
            throw new ResourceNotFound("Erro ao alterar tarefa" + e.getCause());
        }

    }

    public TarefasDTO updateTarefas(TarefasDTO dto, String id) {
        try {
            TarefasEntity entity = tarefasRepository.findById(id).
                    orElseThrow(() -> new ResourceNotFound("Tarefa não encontrada" + id));
            tarefaUpdateConverter.updateTarefas(dto, entity);
          return  tarefaConverter.paraTarefasDTO(tarefasRepository.save(entity));
        } catch (ResourceNotFound e) {
            throw new ResourceNotFound("Erro ao alterar tarefa" + e.getCause());
        }


    }
}
