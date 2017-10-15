package com.github.jntakpe.releasemonitorjava.dao;

import com.github.jntakpe.releasemonitorjava.model.Application;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class ApplicationDAO {

    private final MongoTemplate template;

    public ApplicationDAO(MongoTemplate template) {
        this.template = template;
    }

    public Long count() {
        return template.count(new Query(), Application.class);
    }

    public void deleteAll() {
        template.remove(new Query(), Application.class);
    }

    public void insertAll() {
        template.insertAll(Stream.of(createMockPi(), createSpringBoot()).collect(Collectors.toList()));
    }

    public Application createMockPi() {
        return new Application().setGroup("com.github.jntakpe").setName("spring-boot");
    }

    public Application createSpringBoot() {
        return new Application().setGroup("org.springframework.boot").setName("spring-boot");
    }

    public Application findAny() {
        return template.find(new Query(), Application.class).stream()
                .findAny()
                .orElseThrow(() -> new IllegalStateException("No app found"));
    }

    public List<Application> findAll() {
        return template.findAll(Application.class);
    }
}
