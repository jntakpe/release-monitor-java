package com.github.jntakpe.releasemonitorjava.mapper;

import com.github.jntakpe.releasemonitorjava.model.client.Folder;
import com.github.jntakpe.releasemonitorjava.model.client.FolderChildren;
import org.junit.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class VersionMapperTest {

    @Test
    public void map_shouldMapFolderToVersionList() {
        Folder folder = new Folder().setChildren(
                Stream.of(new FolderChildren().setUri("/1.0.0-RC1"), new FolderChildren().setUri("/1.0.0")).collect(Collectors.toList()));
        assertThat(VersionMapper.map(folder)).containsExactly("1.0.0-RC1", "1.0.0");
    }

}