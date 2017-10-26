package com.github.jntakpe.releasemonitorjava.service;

import com.github.jntakpe.releasemonitorjava.model.Environment;
import com.github.jntakpe.releasemonitorjava.repository.EnvironmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class EnvironmentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnvironmentService.class);

    private final EnvironmentRepository environmentRepository;

    public EnvironmentService(EnvironmentRepository environmentRepository) {
        this.environmentRepository = environmentRepository;
    }

    public Mono<Environment> create(Environment env) {
        return Mono.just(env)
                   .doOnNext(e -> LOGGER.info("Creating {}", e))
                   .flatMap(environmentRepository::save)
                   .doOnSuccess(e -> LOGGER.info("{} created", e));
    }

}
