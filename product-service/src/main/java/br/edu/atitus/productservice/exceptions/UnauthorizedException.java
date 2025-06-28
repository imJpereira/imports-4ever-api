package br.edu.atitus.productservice.exceptions;

import java.util.UUID;

public class UnauthorizedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UnauthorizedException() {
        super("Usuário não tem nível de acesso suficiente");
    }
}
