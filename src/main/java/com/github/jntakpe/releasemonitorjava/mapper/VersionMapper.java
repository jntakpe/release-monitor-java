package com.github.jntakpe.releasemonitorjava.mapper;

import com.github.jntakpe.releasemonitorjava.model.AppVersion;
import com.github.jntakpe.releasemonitorjava.model.VersionType;
import com.github.jntakpe.releasemonitorjava.model.client.Folder;
import com.github.jntakpe.releasemonitorjava.model.client.FolderChildren;
import com.github.jntakpe.releasemonitorjava.utils.PathUtils;
import com.github.zafarkhaja.semver.Version;

import java.util.List;
import java.util.stream.Collectors;

public final class VersionMapper {

    public static List<String> extractRawVersion(Folder folder) {
        return folder.getChildren().stream()
                .map(FolderChildren::getUri)
                .map(PathUtils::removeLeadingSlash)
                .collect(Collectors.toList());
    }

    public static AppVersion map(String input) {
        Version semver = Version.valueOf(input);
        AppVersion version = new AppVersion()
                .setRaw(input)
                .setMajor(semver.getMajorVersion())
                .setMinor(semver.getMinorVersion())
                .setPatch(semver.getPatchVersion())
                .setType(mapType(semver.getPreReleaseVersion()));
        if (version.getType() == VersionType.RELEASE_CANDIDATE) {
            version.setRcNumber(parseRCNumber(semver.getPreReleaseVersion()));
        }
        return version;
    }

    private static VersionType mapType(String type) {
        if (type.isEmpty() || "RELEASE".equalsIgnoreCase(type)) {
            return VersionType.RELEASE;
        } else if (type.startsWith("RC")) {
            return VersionType.RELEASE_CANDIDATE;
        } else if ("SNAPSHOT".equalsIgnoreCase(type)) {
            return VersionType.SNAPSHOT;
        }
        throw new IllegalStateException(String.format("Unable to parse version type %s", type));
    }

    private static Integer parseRCNumber(String preRelease) {
        return Integer.valueOf(preRelease.replace("RC", ""));
    }

}
