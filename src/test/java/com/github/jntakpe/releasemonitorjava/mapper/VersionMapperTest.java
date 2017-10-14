package com.github.jntakpe.releasemonitorjava.mapper;

import com.github.jntakpe.releasemonitorjava.model.AppVersion;
import com.github.jntakpe.releasemonitorjava.model.VersionType;
import com.github.jntakpe.releasemonitorjava.model.client.Folder;
import com.github.jntakpe.releasemonitorjava.model.client.FolderChildren;
import org.junit.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class VersionMapperTest {

    @Test
    public void extractRawVersion_shouldMapFolderToVersionList() {
        Folder folder = new Folder().setChildren(
                Stream.of(new FolderChildren().setUri("/1.0.0-RC1"), new FolderChildren().setUri("/1.0.0")).collect(Collectors.toList()));
        assertThat(VersionMapper.extractRawVersion(folder)).containsExactly("1.0.0-RC1", "1.0.0");
    }

    @Test
    public void map_shouldMapRawStringToRelease() {
        String raw = "1.2.3";
        AppVersion version = VersionMapper.map(raw);
        AppVersion expected = new AppVersion().setRaw(raw).setMajor(1).setMinor(2).setPatch(3).setType(VersionType.RELEASE);
        assertThat(version).isEqualToComparingFieldByField(expected);
    }

    @Test
    public void map_shouldMapRawStringToReleaseWithSuffix() {
        String raw = "1.2.3-RELEASE";
        AppVersion version = VersionMapper.map(raw);
        AppVersion expected = new AppVersion().setRaw(raw).setMajor(1).setMinor(2).setPatch(3).setType(VersionType.RELEASE);
        assertThat(version).isEqualToComparingFieldByField(expected);
    }

    @Test
    public void map_shouldMapRawStringToReleaseWithSuffixIgnoringCase() {
        String raw = "1.2.3-release";
        AppVersion version = VersionMapper.map(raw);
        AppVersion expected = new AppVersion().setRaw(raw).setMajor(1).setMinor(2).setPatch(3).setType(VersionType.RELEASE);
        assertThat(version).isEqualToComparingFieldByField(expected);
    }

    @Test
    public void map_shouldMapRawStringToReleaseCandidate() {
        String raw = "1.2.3-RC1";
        AppVersion version = VersionMapper.map(raw);
        AppVersion expected = new AppVersion()
                .setRaw(raw)
                .setMajor(1)
                .setMinor(2)
                .setPatch(3)
                .setType(VersionType.RELEASE_CANDIDATE)
                .setRcNumber(1);
        assertThat(version).isEqualToComparingFieldByField(expected);
    }

    @Test
    public void map_shouldMapRawStringToReleaseCandidateIgnoringCase() {
        String raw = "1.2.3-RC1";
        AppVersion version = VersionMapper.map(raw);
        AppVersion expected = new AppVersion()
                .setRaw(raw)
                .setMajor(1)
                .setMinor(2)
                .setPatch(3)
                .setType(VersionType.RELEASE_CANDIDATE)
                .setRcNumber(1);
        assertThat(version).isEqualToComparingFieldByField(expected);
    }

    @Test
    public void map_shouldMapRawStringToSnapshot() {
        String raw = "0.1.0-SNAPSHOT";
        AppVersion version = VersionMapper.map(raw);
        AppVersion expected = new AppVersion().setRaw(raw).setMajor(0).setMinor(1).setPatch(0).setType(VersionType.SNAPSHOT);
        assertThat(version).isEqualToComparingFieldByField(expected);
    }

    @Test
    public void map_shouldMapRawStringToSnapshotIgnoring() {
        String raw = "0.1.0-snapshot";
        AppVersion version = VersionMapper.map(raw);
        AppVersion expected = new AppVersion().setRaw(raw).setMajor(0).setMinor(1).setPatch(0).setType(VersionType.SNAPSHOT);
        assertThat(version).isEqualToComparingFieldByField(expected);
    }

}