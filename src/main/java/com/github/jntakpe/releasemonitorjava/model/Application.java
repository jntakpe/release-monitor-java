package com.github.jntakpe.releasemonitorjava.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Application {

    private ObjectId id;

    private String name;

    private String group;

    public ObjectId getId() {
        return id;
    }

    public Application setId(ObjectId id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Application setName(String name) {
        this.name = name;
        return this;
    }

    public String getGroup() {
        return group;
    }

    public Application setGroup(String group) {
        this.group = group;
        return this;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + group.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Application)) {
            return false;
        }
        Application that = (Application) o;
        if (!name.equals(that.name)) {
            return false;
        }
        return group.equals(that.group);
    }

    @Override
    public String toString() {
        return "Application{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", group='" + group + '\'' +
               '}';
    }
}
