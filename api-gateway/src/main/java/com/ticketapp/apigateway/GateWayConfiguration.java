package com.ticketapp.apigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

@Configuration
public class GateWayConfiguration {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder routeLocatorBuilder){
        return routeLocatorBuilder
                .routes()
                .route(predicateSpec -> predicateSpec
                                .path("/user-service/**")
                                .filters(
                                        gatewayFilterSpec -> gatewayFilterSpec
                                                .rewritePath("/user-service/(?<segment>.*)", "/${segment}")
                                )
                                .uri("lb://USER-SERVICE")
                )
                .route(predicateSpec -> predicateSpec
                        .path("/location-service/**")
                        .filters(
                                gatewayFilterSpec -> gatewayFilterSpec
                                        .rewritePath("/location-service/(?<segment>.*)", "/${segment}")
                        )
                        .uri("lb://LOCATION-SERVICE")
                )
                .build();
    }

}
