package com.github.jntakpe.releasemonitorjava.service;

import com.github.jntakpe.releasemonitorjava.model.Application;
import com.github.jntakpe.releasemonitorjava.repository.ApplicationRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ApplicationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationService.class);

    private final ApplicationRepository applicationRepository;

    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    public Mono<Application> create(Application application) {
        return Mono.just(application)
                   .doOnNext(a -> LOGGER.info("Creating {}", a))
                   .flatMap(applicationRepository::save)
                   .doOnSuccess(a -> LOGGER.info("{} created", a));
    }

    public Mono<Application> update(ObjectId id, Application app) {
        return findById(id)
                .doOnNext(a -> LOGGER.info("Updating {}", app))
                .switchIfEmpty(errorIfEmpty(id))
                .flatMap(a -> applicationRepository.save(app))
                .doOnSuccess(a -> LOGGER.info("{} updated", a));
    }

    public Mono<Application> delete(ObjectId id) {
        return findById(id)
                .switchIfEmpty(errorIfEmpty(id))
                .doOnNext(a -> LOGGER.info("Deleting {}", a))
                .flatMap(a -> applicationRepository.delete(a).then(Mono.just(a)))
                .doOnSuccess(a -> LOGGER.info("{} deleted", a));
    }

    public Flux<Application> findAll() {
        return Mono.just(true)
                   .doOnNext(b -> LOGGER.debug("Searching all applications"))
                   .flatMapMany(a -> applicationRepository.findAll())
                   .doOnComplete(() -> LOGGER.debug("All application retrieved"));
    }

    private Mono<Application> findById(ObjectId id) {
        return Mono.just(id)
                   .doOnNext(i -> LOGGER.debug("Searching application with id {}", i))
                   .flatMap(applicationRepository::findById)
                   .doOnNext(a -> LOGGER.debug("{} retrieved with id {}", a, id));
    }

    private Mono<Application> errorIfEmpty(ObjectId id) {
        return Mono.<Application>error(
                new EmptyResultDataAccessException(String.format("Unable to find application matching id %s", id), 1))
                .doOnError(e -> LOGGER.warn(e.getMessage()));
    }
}
