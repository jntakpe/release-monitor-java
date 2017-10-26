package com.github.jntakpe.releasemonitorjava.dao;

import com.github.jntakpe.releasemonitorjava.model.Environment;
import com.github.jntakpe.releasemonitorjava.model.EnvironmentType;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class EnvironmentDAO {

    private final MongoTemplate template;

    public EnvironmentDAO(MongoTemplate template) {
        this.template = template;
    }

    public Long count() {
        return template.count(new Query(), Environment.class);
    }

    public void deleteAll() {
        template.remove(new Query(), Environment.class);
    }

    public void insertAll() {
        template.insertAll(Stream.of(createDxpAssembly(), createDxpIntegration()).collect(Collectors.toList()));
    }

    public Environment findAny() {
        return template.find(new Query(), Environment.class).stream()
                       .findAny()
                       .orElseThrow(() -> new IllegalStateException("No env found"));
    }

    public Environment createDxpAssembly() {
        return new Environment().setName("dxp-azure").setType(EnvironmentType.ASSEMBLY).setUrl("http://dxpasm.edgility.cloud");
    }

    public Environment createDxpIntegration() {
        return new Environment().setName("dxp-azure").setType(EnvironmentType.INTEGRATION).setUrl("http://dxpint.edgility.cloud");
    }

    public List<Environment> findAll() {
        return template.findAll(Environment.class);
    }
}
