package com.github.jntakpe.releasemonitorjava.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Component
@Validated
@ConfigurationProperties(prefix = "artifactory")
public class ArtifactoryProperties {

    @NotNull
    private String host;

    @NotNull
    private String gradleRepository;

    @NotNull
    private ArtifactoryCredentialsProperties credentials;

    public String getHost() {
        return host;
    }

    public ArtifactoryProperties setHost(String host) {
        this.host = host;
        return this;
    }

    public String getGradleRepository() {
        return gradleRepository;
    }

    public ArtifactoryProperties setGradleRepository(String gradleRepository) {
        this.gradleRepository = gradleRepository;
        return this;
    }

    public ArtifactoryCredentialsProperties getCredentials() {
        return credentials;
    }

    public ArtifactoryProperties setCredentials(ArtifactoryCredentialsProperties credentials) {
        this.credentials = credentials;
        return this;
    }

}
