package com.github.jntakpe.releasemonitorjava.model.client;

import java.util.ArrayList;
import java.util.List;

public class FolderDTO {

    private List<FolderChildrenDTO> children = new ArrayList<>();

    public List<FolderChildrenDTO> getChildren() {
        return children;
    }

    public FolderDTO setChildren(List<FolderChildrenDTO> children) {
        this.children = children;
        return this;
    }
}
