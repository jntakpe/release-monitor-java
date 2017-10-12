package com.github.jntakpe.releasemonitorjava.model.client;

import java.util.ArrayList;
import java.util.List;

public class Folder {

    private List<FolderChildren> children = new ArrayList<>();

    public List<FolderChildren> getChildren() {
        return children;
    }

    public Folder setChildren(List<FolderChildren> children) {
        this.children = children;
        return this;
    }
}
