package com.github.jntakpe.releasemonitorjava.service;

import com.github.jntakpe.releasemonitorjava.dao.EnvironmentDAO;
import com.github.jntakpe.releasemonitorjava.model.Environment;
import com.github.jntakpe.releasemonitorjava.model.EnvironmentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EnvironmentServiceTest {

    @Autowired
    private EnvironmentService environmentService;

    @Autowired
    private EnvironmentDAO environmentDAO;

    @Before
    public void setup() {
        environmentDAO.deleteAll();
        environmentDAO.insertAll();
    }

    @Test
    public void create_shouldCreateANewEnvironment() {
        Long initCount = environmentDAO.count();
        Environment environment = new Environment().setName("bar").setType(EnvironmentType.ASSEMBLY).setUrl("foo");
        StepVerifier.create(environmentService.create(environment))
                    .consumeNextWith(e -> {
                        assertThat(e.getId()).isNotNull();
                        assertThat(e).isEqualTo(environment);
                        assertThat(environmentDAO.count()).isEqualTo(initCount + 1);
                    })
                    .verifyComplete();
    }

    @Test
    public void create_shouldFailCuzEnvironmentExist() {
        StepVerifier.create(environmentService.create(environmentDAO.createDxpAssembly()))
                    .verifyError(DuplicateKeyException.class);
    }

}