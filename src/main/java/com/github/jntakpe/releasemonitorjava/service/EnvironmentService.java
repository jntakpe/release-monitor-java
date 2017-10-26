package com.github.jntakpe.releasemonitorjava.service;

import com.github.jntakpe.releasemonitorjava.model.Environment;
import com.github.jntakpe.releasemonitorjava.repository.EnvironmentRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
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

    public Mono<Environment> update(ObjectId id, Environment env) {
        return findById(id)
                .map(e -> checkIdsMatches(id, env))
                .switchIfEmpty(errorIfEmpty(id))
                .doOnNext(e -> LOGGER.info("Updating {}", e))
                .flatMap(environmentRepository::save)
                .doOnSuccess(e -> LOGGER.info("{} updated", e));
    }

    public Mono<Environment> delete(ObjectId id) {
        return findById(id)
                .switchIfEmpty(errorIfEmpty(id))
                .doOnNext(e -> LOGGER.info("Deleting {}", e))
                .flatMap(e -> environmentRepository.delete(e).then(Mono.just(e)))
                .doOnSuccess(e -> LOGGER.info("{} deleted", e));
    }

    public Flux<Environment> findAll() {
        return Mono.just(true)
                   .doOnNext(b -> LOGGER.debug("Searching all environments"))
                   .flatMapMany(e -> environmentRepository.findAll())
                   .doOnComplete(() -> LOGGER.debug("All environment retrieved"));
    }

    private Mono<Environment> findById(ObjectId id) {
        return Mono.just(id)
                   .doOnNext(i -> LOGGER.debug("Searching environment with id {}", i))
                   .flatMap(environmentRepository::findById)
                   .doOnNext(e -> LOGGER.debug("{} retrieved with id {}", e, id));
    }

    private Mono<Environment> errorIfEmpty(ObjectId id) {
        return Mono.<Environment>error(
                new EmptyResultDataAccessException(String.format("Unable to find environment matching id %s", id), 1))
                .doOnError(e -> LOGGER.warn(e.getMessage()));
    }

    private Environment checkIdsMatches(ObjectId id, Environment env) {
        if (env.getId() != null && !env.getId().equals(id)) {
            throw new IllegalStateException(String.format("Id %s doesn't match environment's %s id", id, env));
        }
        return env.setId(id);
    }
}
