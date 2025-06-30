package br.edu.atitus.orderservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(
        name = "product-service",
        fallback = ProductFallback.class
)
public interface ProductClient {

    @GetMapping("/products/{product}/BRL")
    ProductResponse getProduct(
            @PathVariable("product") UUID product
    );

}
