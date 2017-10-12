package com.github.jntakpe.releasemonitorjava.service;

import com.github.jntakpe.releasemonitorjava.dao.ApplicationDAO;
import com.github.jntakpe.releasemonitorjava.model.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.test.StepVerifier;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ApplicationServiceTest {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private ApplicationDAO applicationDAO;

    @Before
    public void setUp() {
        applicationDAO.deleteAll();
        applicationDAO.insertAll();
    }

    @Test
    public void create_shouldCreateANewApplication() {
        Long initCount = applicationDAO.count();
        Application application = new Application().setName("foo").setGroup("bar");
        StepVerifier.create(applicationService.create(application))
                    .consumeNextWith(a -> {
                        assertThat(a.getId()).isNotNull();
                        assertThat(a).isEqualTo(application);
                        assertThat(initCount + 1).isEqualTo(applicationDAO.count());
                    })
                    .verifyComplete();
    }

    @Test
    public void create_shouldFailCuzApplicationExist() {
        StepVerifier.create(applicationService.create(applicationDAO.createMockPi()))
                    .verifyError(DuplicateKeyException.class);
    }

    @Test
    public void findAll_shouldRetrieveSome() {
        StepVerifier.create(applicationService.findAll())
                    .expectNextCount(applicationDAO.count())
                    .recordWith(ArrayList::new)
                    .consumeRecordedWith(r -> assertThat(r).contains(applicationDAO.createMockPi(), applicationDAO.createSpringBoot()))
                    .verifyComplete();
    }

    @Test
    public void findAll_shouldBeEmpty() {
        applicationDAO.deleteAll();
        StepVerifier.create(applicationService.findAll())
                    .expectNextCount(0)
                    .verifyComplete();
    }

}