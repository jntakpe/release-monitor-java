package com.github.jntakpe.releasemonitorjava.service;

import com.github.jntakpe.releasemonitorjava.model.Application;
import com.github.jntakpe.releasemonitorjava.repository.ApplicationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
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

}
