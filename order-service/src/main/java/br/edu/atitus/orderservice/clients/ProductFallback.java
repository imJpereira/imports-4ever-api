package br.edu.atitus.orderservice.clients;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProductFallback implements ProductClient {

    @Override
    public ProductResponse getProduct(UUID product) {
        return null;
    }
}
