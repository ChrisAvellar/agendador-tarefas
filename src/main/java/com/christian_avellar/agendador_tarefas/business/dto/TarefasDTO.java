package com.christian_avellar.agendador_tarefas.business.dto;

import com.christian_avellar.agendador_tarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TarefasDTO {

    private String id;
    private String nomeTarefa;
    private String descricao;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-YYY HH:mm:ss")
    private LocalDateTime dataCriacao;
    private LocalDateTime dataEvento;
    private String emailUsuario;
    private LocalDateTime dataAlteracao;
    private StatusNotificacaoEnum statusNotificacaoEnum;
}
