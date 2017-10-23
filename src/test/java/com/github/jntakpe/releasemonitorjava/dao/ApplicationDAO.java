package com.github.jntakpe.releasemonitorjava.dao;

import com.github.jntakpe.releasemonitorjava.model.AppVersion;
import com.github.jntakpe.releasemonitorjava.model.Application;
import com.github.jntakpe.releasemonitorjava.model.VersionType;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collections;
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
        template.insertAll(Stream.of(createMockPi(), createReleaseMonitor()).collect(Collectors.toList()));
    }

    public Application createMockPi() {
        return new Application().setGroup("com.github.jntakpe").setName("mockpi");
    }

    public Application createReleaseMonitor() {
        return new Application().setGroup("com.github.jntakpe")
                                .setName("release-monitor")
                                .setVersions(Collections.singletonList(version()));
    }

    public Application findAny() {
        return template.find(new Query(), Application.class).stream()
                       .findAny()
                       .orElseThrow(() -> new IllegalStateException("No app found"));
    }

    public List<Application> findAll() {
        return template.findAll(Application.class);
    }

    public Application findById(ObjectId id) {
        return template.findById(id, Application.class);
    }

    private AppVersion version() {
        return new AppVersion().setRaw("1.2.3").setMajor(1).setMinor(2).setPatch(3).setType(VersionType.RELEASE);
    }

}
