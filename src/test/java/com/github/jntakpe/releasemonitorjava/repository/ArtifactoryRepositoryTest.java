package com.github.jntakpe.releasemonitorjava.repository;

import com.github.jntakpe.releasemonitorjava.model.Application;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ArtifactoryRepositoryTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8089);

    @Autowired
    private ArtifactoryRepository artifactoryRepository;

    @Test
    public void findVersions_shouldRetrieveVersions() {
        Application app = new Application().setGroup("com.github.jntakpe").setName("release-monitor");
        StepVerifier.create(artifactoryRepository.findVersions(app))
                    .consumeNextWith(v -> assertThat(v).isNotEmpty())
                    .verifyComplete();
    }

    @Test
    public void findVersions_shouldFailCuzUnknownApplication() {
        Application app = new Application().setGroup("com.github.jntakpe").setName("service-unknown");
        StepVerifier.create(artifactoryRepository.findVersions(app))
                    .verifyError(WebClientResponseException.class);
    }

    @Test
    public void findVersions_shouldFilterMavenMetadata() {
        Application app = new Application().setGroup("com.github.jntakpe").setName("release-monitor");
        StepVerifier.create(artifactoryRepository.findVersions(app))
                    .consumeNextWith(v -> assertThat(v).doesNotContain("maven-metadata.xml"))
                    .verifyComplete();
    }
}