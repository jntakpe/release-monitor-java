package com.github.jntakpe.releasemonitorjava.config;

import com.github.jntakpe.releasemonitorjava.config.properties.ArtifactoryCredentialsProperties;
import com.github.jntakpe.releasemonitorjava.config.properties.ArtifactoryProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Base64;

@Configuration
public class WebClientConfiguration {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private static final String BASE_API = "/artifactory/api";

    private static final String STORAGE_API = "/storage";

    private final ArtifactoryProperties artifactoryProperties;

    public WebClientConfiguration(ArtifactoryProperties artifactoryProperties) {
        this.artifactoryProperties = artifactoryProperties;
    }

    @Bean
    public WebClient artifactoryClient() {
        return WebClient.builder()
                        .baseUrl(baseUrl())
                        .defaultHeader(AUTHORIZATION_HEADER, buildBasicHeaderValue())
                        .build();
    }

    private String baseUrl() {
        return artifactoryProperties.getHost() + BASE_API + STORAGE_API + artifactoryProperties.getGradleRepository();
    }

    private String buildBasicHeaderValue() {
        ArtifactoryCredentialsProperties credentials = artifactoryProperties.getCredentials();
        String concat = credentials.getUsername() + ":" + credentials.getPassword();
        return "Basic " + Base64.getEncoder().encodeToString(concat.getBytes());
    }

}
