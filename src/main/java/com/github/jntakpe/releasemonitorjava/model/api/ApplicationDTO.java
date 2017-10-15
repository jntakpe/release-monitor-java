package com.github.jntakpe.releasemonitorjava.model.api;

import com.github.jntakpe.releasemonitorjava.model.AppVersion;

import java.util.ArrayList;
import java.util.List;

public class ApplicationDTO {

    private String id;

    private String group;

    private String name;

    private List<AppVersion> versions = new ArrayList<>();

    public String getId() {
        return id;
    }

    public ApplicationDTO setId(String id) {
        this.id = id;
        return this;
    }

    public String getGroup() {
        return group;
    }

    public ApplicationDTO setGroup(String group) {
        this.group = group;
        return this;
    }

    public String getName() {
        return name;
    }

    public ApplicationDTO setName(String name) {
        this.name = name;
        return this;
    }

    public List<AppVersion> getVersions() {
        return versions;
    }

    public ApplicationDTO setVersions(List<AppVersion> versions) {
        this.versions = versions;
        return this;
    }
}
