package com.github.jntakpe.releasemonitorjava.config;

import com.github.jntakpe.releasemonitorjava.web.ApplicationHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static com.github.jntakpe.releasemonitorjava.web.UriConstants.API;
import static com.github.jntakpe.releasemonitorjava.web.UriConstants.APPLICATIONS;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RoutesConfiguration {

    private final ApplicationHandler applicationHandler;

    public RoutesConfiguration(ApplicationHandler applicationHandler) {
        this.applicationHandler = applicationHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> apiRouter() {
        return nest(path(API),
                    nest(path(APPLICATIONS),
                         nest(accept(MediaType.APPLICATION_JSON), route(GET("/"), applicationHandler::findAll)
                                 .and(route(POST("/"), applicationHandler::create))
                                 .and(route(PUT("/{id}"), applicationHandler::update))
                                 .and(route(DELETE("/{id}"), applicationHandler::delete)))
                                 .andNest(accept(MediaType.TEXT_EVENT_STREAM), route(GET("/"), applicationHandler::monitor))
                    ));
    }
}
