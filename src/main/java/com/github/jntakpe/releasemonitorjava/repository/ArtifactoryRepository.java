package com.github.jntakpe.releasemonitorjava.repository;

import com.github.jntakpe.releasemonitorjava.mapper.VersionMapper;
import com.github.jntakpe.releasemonitorjava.model.AppVersion;
import com.github.jntakpe.releasemonitorjava.model.Application;
import com.github.jntakpe.releasemonitorjava.model.client.Folder;
import com.github.jntakpe.releasemonitorjava.utils.PathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

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

    public Flux<AppVersion> findVersions(Application app) {
        LOGGER.debug("Searching {} versions", app);
        return findRawVersions(app)
                .map(VersionMapper::map)
                .sort()
                .doOnNext(v -> LOGGER.debug("Versions {} updated", v));
    }

    private Flux<String> findRawVersions(Application app) {
        return artifactoryClient.get().uri(createFolderPath(app)).retrieve()
                .bodyToMono(Folder.class)
                .map(VersionMapper::extractRawVersion)
                .flatMapMany(Flux::fromIterable)
                .filter(v -> !isMavenMetadata(v));
    }

    private String createFolderPath(Application app) {
        return STORAGE_API + GRADLE_REPO + "/" + PathUtils.dotToSlash(app.getGroup()) + "/" + app.getName();
    }

    private boolean isMavenMetadata(String input) {
        return MAVEN_METADATA.equals(input);
    }
}
