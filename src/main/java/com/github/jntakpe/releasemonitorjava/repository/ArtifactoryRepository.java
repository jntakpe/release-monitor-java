package com.github.jntakpe.releasemonitorjava.repository;

import com.github.jntakpe.releasemonitorjava.mapper.VersionMapper;
import com.github.jntakpe.releasemonitorjava.model.Application;
import com.github.jntakpe.releasemonitorjava.model.client.Folder;
import com.github.jntakpe.releasemonitorjava.utils.PathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ArtifactoryRepository {

    private static final String STORAGE_API = "/storage";

    private static final String GRADLE_REPO = "/gradle-repo-local";

    private static final String MAVEN_METADATA = "maven-metadata.xml";

    private static final Logger LOGGER = LoggerFactory.getLogger(ArtifactoryRepository.class);

    private final WebClient artifactoryClient;

    public ArtifactoryRepository(WebClient artifactoryClient) {
        this.artifactoryClient = artifactoryClient;
    }

    public Mono<List<String>> findVersions(Application app) {
        LOGGER.debug("Searching {} versions", app);
        return artifactoryClient.get().uri(createFolderPath(app)).retrieve()
                                .bodyToMono(Folder.class)
                                .map(VersionMapper::map)
                                .map(this::removeMavenMetadata)
                                .doOnNext(v -> LOGGER.debug("Versions {} updated", v));
    }

    private String createFolderPath(Application app) {
        return STORAGE_API + GRADLE_REPO + "/" + PathUtils.dotToSlash(app.getGroup()) + "/" + app.getName();
    }

    private List<String> removeMavenMetadata(List<String> versions) {
        return versions.stream().filter(v -> !isMavenMetadata(v)).collect(Collectors.toList());
    }

    private boolean isMavenMetadata(String input) {
        return MAVEN_METADATA.equals(input);
    }
}
