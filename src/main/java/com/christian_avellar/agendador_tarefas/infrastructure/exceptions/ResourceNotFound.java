package com.christian_avellar.agendador_tarefas.infrastructure.exceptions;



public class ResourceNotFound extends RuntimeException {
    public ResourceNotFound(String mensagem) {
        super(mensagem);
    }
    public ResourceNotFound(String mensagem, Throwable throwable) {
        super(mensagem, throwable);
    }
}
