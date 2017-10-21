package com.github.jntakpe.releasemonitorjava.web;

import com.github.jntakpe.releasemonitorjava.dao.ApplicationDAO;
import com.github.jntakpe.releasemonitorjava.mapper.ApplicationMapper;
import com.github.jntakpe.releasemonitorjava.model.api.ApplicationDTO;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static com.github.jntakpe.releasemonitorjava.web.UriConstants.API;
import static com.github.jntakpe.releasemonitorjava.web.UriConstants.APPLICATIONS;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationHandlerTest {

    @Autowired
    private ApplicationDAO applicationDAO;

    @LocalServerPort
    private Integer port;

    private WebTestClient client;

    @Before
    public void setup() {
        applicationDAO.deleteAll();
        applicationDAO.insertAll();
        client = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();
    }

    @Test
    public void findAll_shouldFindAll() {
        client.get()
                .uri(UriConstants.API + UriConstants.APPLICATIONS)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ApplicationDTO.class).consumeWith(l -> {
            List<ApplicationDTO> apps = l.getResponseBody();
            assertThat(apps).isNotEmpty();
            assertThat(apps).hasSize(applicationDAO.count().intValue());
        });
    }

    @Test
    public void findAll_shouldFindNone() {
        applicationDAO.deleteAll();
        client.get()
                .uri(UriConstants.API + UriConstants.APPLICATIONS)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ApplicationDTO.class).consumeWith(l -> {
            List<ApplicationDTO> apps = l.getResponseBody();
            assertThat(apps).isEmpty();
        });
    }

    @Test
    public void create_shouldCreateANewApplication() {
        ApplicationDTO input = new ApplicationDTO().setGroup("foo").setName("bar");
        Long initCount = applicationDAO.count();
        client.post()
                .uri(UriConstants.API + UriConstants.APPLICATIONS)
                .syncBody(input)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody(ApplicationDTO.class).consumeWith(r -> {
            ApplicationDTO app = r.getResponseBody();
            assertThat(app).isNotNull();
            assertThat(app.getId()).isNotNull();
            assertThat(app).isEqualToIgnoringGivenFields(input, "id");
            assertThat(applicationDAO.count()).isEqualTo(initCount + 1);
        });
    }

    @Test
    public void create_shouldFailCuzApplicationExists() {
        client.post()
                .uri(API + APPLICATIONS)
                .syncBody(applicationDAO.createMockPi())
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    public void update_shouldUpdateExisting() {
        String name = "updated";
        ApplicationDTO input = ApplicationMapper.map(applicationDAO.findAny().setName(name));
        client.put()
                .uri(UriConstants.API + UriConstants.APPLICATIONS + "/{id}", input.getId())
                .syncBody(input)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody(ApplicationDTO.class).consumeWith(r -> {
            ApplicationDTO app = r.getResponseBody();
            assertThat(app).isNotNull();
            assertThat(app.getName()).isEqualTo(name);
            assertThat(app).isEqualToIgnoringGivenFields(input, "name");
            assertThat(applicationDAO.findById(new ObjectId(input.getId())).getName()).isEqualTo(name);
        });
    }

    @Test
    public void update_shouldFailIfWrongId() {
        client.put()
                .uri(UriConstants.API + UriConstants.APPLICATIONS + "/{id}", new ObjectId())
                .syncBody(ApplicationMapper.map(applicationDAO.findAny()))
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    public void delete_shouldDelete() {
        Long initCount = applicationDAO.count();
        String id = applicationDAO.findAny().getId().toString();
        client.delete()
                .uri(UriConstants.API + UriConstants.APPLICATIONS + "/{id}", id)
                .exchange()
                .expectStatus().isNoContent()
                .expectBody().consumeWith(r -> assertThat(applicationDAO.count()).isEqualTo(initCount - 1));
    }

}