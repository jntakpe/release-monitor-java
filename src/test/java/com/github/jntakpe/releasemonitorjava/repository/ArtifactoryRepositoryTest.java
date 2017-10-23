package com.github.jntakpe.releasemonitorjava.repository;

import com.github.jntakpe.releasemonitorjava.model.AppVersion;
import com.github.jntakpe.releasemonitorjava.model.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.test.StepVerifier;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ArtifactoryRepositoryTest {

    @Autowired
    private ArtifactoryRepository artifactoryRepository;

    @Test
    public void findVersions_shouldRetrieveVersions() {
        int versionsSizeWithoutMavenMetadata = 8 - 1;
        Application app = new Application().setGroup("com.github.jntakpe").setName("release-monitor");
        StepVerifier.create(artifactoryRepository.findVersions(app))
                .recordWith(ArrayList::new)
                .expectNextCount(versionsSizeWithoutMavenMetadata)
                .consumeRecordedWith(r -> assertThat(r.stream().map(AppVersion::getRaw)).contains("0.1.0-RC1", "0.1.0-SNAPSHOT"))
                .verifyComplete();
    }

    @Test
    public void findVersions_shouldFailCuzUnknownApplication() {
        Application app = new Application().setGroup("com.github.jntakpe").setName("service-unknown");
        StepVerifier.create(artifactoryRepository.findVersions(app))
                .verifyError(WebClientResponseException.class);
    }

    @Test
    public void findVersions_shouldRetrieveVersionsSorted() {
        int versionsSizeWithoutMavenMetadata = 8 - 1;
        Application app = new Application().setGroup("com.github.jntakpe").setName("release-monitor");
        StepVerifier.create(artifactoryRepository.findVersions(app))
                .recordWith(ArrayList::new)
                .expectNextCount(versionsSizeWithoutMavenMetadata)
                .consumeRecordedWith(r -> assertThat(new ArrayList<>(r)).isSorted())
                .verifyComplete();
    }

}