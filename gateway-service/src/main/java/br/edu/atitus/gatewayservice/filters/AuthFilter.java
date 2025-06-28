package br.edu.atitus.gatewayservice.filters;

import br.edu.atitus.gatewayservice.components.JwtUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class AuthFilter implements GlobalFilter, Ordered {

    private static final List<String> PROTECTED_ROUTES = List.of("/ws/");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        var request = exchange.getRequest();
        var path = request.getURI().getPath();

        if (!PROTECTED_ROUTES.stream().anyMatch(path::startsWith))
            return chain.filter(exchange);

        //Verifica se existe um token e valida
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            var payload = JwtUtil.validateToken(jwt);
            if (payload != null) {
                ServerHttpRequest modifiedRequest = request.mutate()
                        .header("X-User-Id", String.valueOf(payload.get("id", Long.class)))
                        .header("X-User-Type", String.valueOf(payload.get("type", Integer.class)))
                        .header("X-User-Email", payload.get("email", String.class))
                        .build();
                return chain.filter(exchange.mutate().request(modifiedRequest).build());
            }
        }

        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
