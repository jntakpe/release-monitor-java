package com.github.jntakpe.releasemonitorjava.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@CompoundIndex(name = "name_type", def = "{'name' : 1, 'type': 1}", unique = true)
public class Environment {

    private ObjectId id;

    private String name;

    private EnvironmentType type;

    private String url;

    public ObjectId getId() {
        return id;
    }

    public Environment setId(ObjectId id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Environment setName(String name) {
        this.name = name;
        return this;
    }

    public EnvironmentType getType() {
        return type;
    }

    public Environment setType(EnvironmentType type) {
        this.type = type;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Environment setUrl(String url) {
        this.url = url;
        return this;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Environment)) {
            return false;
        }
        Environment that = (Environment) o;
        if (name != null ? !name.equals(that.name) : that.name != null) {
            return false;
        }
        return type == that.type;
    }

    @Override
    public String toString() {
        return "Environment{" +
               "name='" + name + '\'' +
               ", type=" + type +
               ", url='" + url + '\'' +
               '}';
    }
}
