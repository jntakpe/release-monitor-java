package com.github.jntakpe.releasemonitorjava.mapper;

import com.github.jntakpe.releasemonitorjava.model.client.Folder;
import com.github.jntakpe.releasemonitorjava.model.client.FolderChildren;
import com.github.jntakpe.releasemonitorjava.utils.PathUtils;

import java.util.List;
import java.util.stream.Collectors;

public final class VersionMapper {

    public static List<String> map(Folder folder) {
        return folder.getChildren().stream().map(FolderChildren::getUri).map(PathUtils::removeLeadingSlash).collect(Collectors.toList());
    }

}
