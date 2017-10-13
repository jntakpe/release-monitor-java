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
        LOGGER.info("Creating {}", application);
        return applicationRepository.save(application)
                .doOnSuccess(a -> LOGGER.info("{} created", a));
    }

    public Mono<Application> update(ObjectId id, Application app) {
        LOGGER.info("Updating {}", app);
        return findById(id)
                .switchIfEmpty(errorIfEmpty(id))
                .flatMap(a -> applicationRepository.save(app))
                .doOnSuccess(a -> LOGGER.info("{} updated", a));
    }

    public Flux<Application> findAll() {
        LOGGER.debug("Searching all applications");
        return applicationRepository.findAll()
                .doOnComplete(() -> LOGGER.debug("All application retrieved"));
    }

    private Mono<Application> findById(ObjectId id) {
        LOGGER.debug("Searching application with id {}", id);
        return applicationRepository.findById(id)
                .doOnSuccess(a -> LOGGER.debug("{} retrieved with id {}", a, id));
    }

    private Mono<Application> errorIfEmpty(ObjectId id) {
        String message = String.format("Unable to find application matching id %s", id);
        LOGGER.warn(message);
        return Mono.error(new EmptyResultDataAccessException(message, 1));
    }
}
