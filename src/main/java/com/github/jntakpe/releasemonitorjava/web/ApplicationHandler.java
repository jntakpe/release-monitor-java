package com.github.jntakpe.releasemonitorjava.web;

import com.github.jntakpe.releasemonitorjava.mapper.ApplicationMapper;
import com.github.jntakpe.releasemonitorjava.model.api.ApplicationDTO;
import com.github.jntakpe.releasemonitorjava.service.ApplicationService;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

import static com.github.jntakpe.releasemonitorjava.web.UriConstants.API;
import static com.github.jntakpe.releasemonitorjava.web.UriConstants.APPLICATIONS;

@Component
public class ApplicationHandler {

    private final ApplicationService applicationService;

    public ApplicationHandler(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    public Mono<ServerResponse> findAll(ServerRequest serverRequest) {
        return ServerResponse.ok().body(applicationService.findAll().map(ApplicationMapper::map), ApplicationDTO.class);
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(ApplicationDTO.class)
                      .map(ApplicationMapper::map)
                      .flatMap(applicationService::create)
                      .map(ApplicationMapper::map)
                      .flatMap(a -> ServerResponse.created(URI.create(API + APPLICATIONS + "/" + a.getId())).syncBody(a));
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        return request.bodyToMono(ApplicationDTO.class)
                      .map(ApplicationMapper::map)
                      .map(a -> Pair.of(RequestUtils.idFromPath(request), a))
                      .flatMap(p -> applicationService.update(p.getFirst(), p.getSecond()))
                      .map(ApplicationMapper::map)
                      .flatMap(a -> ServerResponse.ok().syncBody(a));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        return Mono.just(RequestUtils.idFromPath(request))
                   .flatMap(applicationService::delete)
                   .flatMap(a -> ServerResponse.noContent().build());
    }

}
