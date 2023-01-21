package com.ticketapp.apigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GateWayConfiguration {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder routeLocatorBuilder){
        return routeLocatorBuilder
                .routes()
                .route(predicateSpec -> predicateSpec
                                .path("/user-service/**")
                                .filters(f -> f.stripPrefix(1))
                                .uri("lb://USER-SERVICE")
                )
                .route(predicateSpec -> predicateSpec
                        .path("/location-service/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://LOCATION-SERVICE")
                )
                .build();
    }

}
