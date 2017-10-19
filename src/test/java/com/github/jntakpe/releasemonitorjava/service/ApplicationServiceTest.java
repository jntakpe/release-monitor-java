package com.github.jntakpe.releasemonitorjava.service;

import com.github.jntakpe.releasemonitorjava.dao.ApplicationDAO;
import com.github.jntakpe.releasemonitorjava.model.Application;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
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
        Application application = new Application().setGroup("bar").setName("foo");
        StepVerifier.create(applicationService.create(application))
                    .consumeNextWith(a -> {
                        assertThat(a.getId()).isNotNull();
                        assertThat(a).isEqualTo(application);
                        assertThat(applicationDAO.count()).isEqualTo(initCount + 1);
                    })
                    .verifyComplete();
    }

    @Test
    public void create_shouldFailCuzApplicationExist() {
        StepVerifier.create(applicationService.create(applicationDAO.createMockPi()))
                    .verifyError(DuplicateKeyException.class);
    }

    @Test
    public void update_shouldUpdateExisting() {
        Long initCount = applicationDAO.count();
        Application app = applicationDAO.findAny();
        String updatedName = "updated";
        StepVerifier.create(applicationService.update(app.getId(), app.setName(updatedName)))
                .consumeNextWith(a -> {
                    assertThat(a.getId()).isEqualTo(app.getId());
                    assertThat(a.getName()).isEqualTo(updatedName);
                    assertThat(applicationDAO.count()).isEqualTo(initCount);
                })
                .verifyComplete();
    }

    @Test
    public void update_shouldFailIfIdMissing() {
        StepVerifier.create(applicationService.update(new ObjectId(), applicationDAO.findAny()))
                .verifyError(EmptyResultDataAccessException.class);
    }

    @Test
    public void update_shouldFailIfIdDoesNotMatch() {
        Application app = applicationDAO.findAny();
        StepVerifier.create(applicationService.update(app.getId(), app.setId(new ObjectId()).setName("updated")))
                    .verifyError(IllegalStateException.class);
    }

    @Test
    public void update_shouldSetIdIfNull() {
        Application app = applicationDAO.findAny();
        ObjectId id = app.getId();
        StepVerifier.create(applicationService.update(id, app.setId(null)))
                    .consumeNextWith(a -> assertThat(a.getId()).isEqualTo(id))
                    .verifyComplete();
    }

    @Test
    public void delete_shouldRemoveApplication() {
        Long initCount = applicationDAO.count();
        Application app = applicationDAO.findAny();
        StepVerifier.create(applicationService.delete(app.getId()))
                .consumeNextWith(a -> {
                    assertThat(applicationDAO.count()).isEqualTo(initCount - 1);
                    assertThat(applicationDAO.findAll()).doesNotContain(app);
                })
                .verifyComplete();
    }

    @Test
    public void delete_shouldFailIfIdMissing() {
        StepVerifier.create(applicationService.delete(new ObjectId()))
                .verifyError(EmptyResultDataAccessException.class);
    }

    @Test
    public void findAll_shouldRetrieveSome() {
        StepVerifier.create(applicationService.findAll())
                    .recordWith(ArrayList::new)
                    .expectNextCount(applicationDAO.count())
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