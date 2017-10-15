package com.github.jntakpe.releasemonitorjava.config.properties;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
public class ArtifactoryCredentialsProperties {

    @NotNull
    private String username;

    @NotNull
    private String password;

    public String getUsername() {
        return username;
    }

    public ArtifactoryCredentialsProperties setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public ArtifactoryCredentialsProperties setPassword(String password) {
        this.password = password;
        return this;
    }

}
