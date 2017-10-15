package com.github.jntakpe.releasemonitorjava.web;

import com.github.jntakpe.releasemonitorjava.dao.ApplicationDAO;
import com.github.jntakpe.releasemonitorjava.model.api.ApplicationDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

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
    public void create_shouldCreateANewApplication() {
        ApplicationDTO input = new ApplicationDTO().setGroup("foo").setName("bar");
        client.post()
              .uri(UriConstants.API + UriConstants.APPLICATIONS)
              .syncBody(input)
              .exchange()
              .expectStatus().isCreated()
              .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
              .expectBody(ApplicationDTO.class).consumeWith(r -> {
            ApplicationDTO app = r.getResponseBody();
            assertThat(app.getId()).isNotNull();
            assertThat(app).isEqualToIgnoringGivenFields(input, "id");
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

}