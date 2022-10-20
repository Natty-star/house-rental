package cs590.gatewayservice.security;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class AdminRouterValidator {
    public static final List<String> openApiEndpoints = List.of(
            "/accounts/getAll"
    );
    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .anyMatch(uri -> request.getURI().getPath().contains(uri) );
}