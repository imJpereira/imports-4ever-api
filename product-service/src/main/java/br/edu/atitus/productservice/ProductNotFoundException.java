package br.edu.atitus.productservice;

import java.util.UUID;

public class ProductNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ProductNotFoundException(UUID id) {
        super("Produto não encontrado com ID: " + id);
    }
}
