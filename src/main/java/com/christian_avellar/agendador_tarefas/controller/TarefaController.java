package com.christian_avellar.agendador_tarefas.controller;

import com.christian_avellar.agendador_tarefas.business.TarefaService;
import com.christian_avellar.agendador_tarefas.business.dto.TarefasDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tarefas")
@RequiredArgsConstructor
public class TarefaController {

    private final TarefaService tarefasService;

    @PostMapping
    public ResponseEntity<TarefasDTO>gravarTarefa(@RequestBody TarefasDTO dto,
                                                  @RequestHeader("Authorization") String token){

        return ResponseEntity.ok(tarefasService.gravarTarefa(token,dto));
    }

    @GetMapping("/eventos")
    public ResponseEntity<List<TarefasDTO>> listarTarefasPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime dataInicio ,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime dataFim
            ){
        return ResponseEntity.ok(tarefasService.buscaTarefasPorPeriodo(dataInicio,dataFim));
    }

    @GetMapping
    public  ResponseEntity<List<TarefasDTO>> buscaTarefasPorEmail(@RequestHeader("Authorization")String token){
        List<TarefasDTO> tarefas = tarefasService.buscaTarefasPorEmail(token);
        return ResponseEntity.ok(tarefas);
    }


}
