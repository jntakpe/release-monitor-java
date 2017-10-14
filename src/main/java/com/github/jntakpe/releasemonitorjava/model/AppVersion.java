package com.github.jntakpe.releasemonitorjava.model;

import java.util.Comparator;

public class AppVersion implements Comparable<AppVersion> {

    private String raw;

    private Integer major;

    private Integer minor;

    private Integer patch;

    private VersionType type;

    private Integer rcNumber;

    public String getRaw() {
        return raw;
    }

    public AppVersion setRaw(String raw) {
        this.raw = raw;
        return this;
    }

    public Integer getMajor() {
        return major;
    }

    public AppVersion setMajor(Integer major) {
        this.major = major;
        return this;
    }

    public Integer getMinor() {
        return minor;
    }

    public AppVersion setMinor(Integer minor) {
        this.minor = minor;
        return this;
    }

    public Integer getPatch() {
        return patch;
    }

    public AppVersion setPatch(Integer patch) {
        this.patch = patch;
        return this;
    }

    public VersionType getType() {
        return type;
    }

    public AppVersion setType(VersionType type) {
        this.type = type;
        return this;
    }

    public Integer getRcNumber() {
        return rcNumber;
    }

    public AppVersion setRcNumber(Integer rcNumber) {
        this.rcNumber = rcNumber;
        return this;
    }

    @Override
    public int compareTo(AppVersion other) {
        if (other == null) {
            return 1;
        }
        return Comparator.comparingInt(AppVersion::getMajor)
                .thenComparing(AppVersion::getMinor)
                .thenComparing(AppVersion::getPatch)
                .thenComparing(AppVersion::getType)
                .thenComparing((o1, o2) -> (o1.getRcNumber() != null && o2.getRcNumber() != null) ?
                        o1.getRcNumber().compareTo(o2.getRcNumber()) : 0)
                .compare(this, other);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppVersion)) return false;

        AppVersion that = (AppVersion) o;

        if (major != null ? !major.equals(that.major) : that.major != null) return false;
        if (minor != null ? !minor.equals(that.minor) : that.minor != null) return false;
        if (patch != null ? !patch.equals(that.patch) : that.patch != null) return false;
        if (type != that.type) return false;
        return rcNumber != null ? rcNumber.equals(that.rcNumber) : that.rcNumber == null;
    }

    @Override
    public int hashCode() {
        int result = major != null ? major.hashCode() : 0;
        result = 31 * result + (minor != null ? minor.hashCode() : 0);
        result = 31 * result + (patch != null ? patch.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (rcNumber != null ? rcNumber.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AppVersion{" +
                "raw='" + raw + '\'' +
                '}';
    }
}
