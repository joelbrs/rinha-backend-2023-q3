package br.com.joelf.rinha_backend_2023_q3.infrastructure.database.exceptions;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
