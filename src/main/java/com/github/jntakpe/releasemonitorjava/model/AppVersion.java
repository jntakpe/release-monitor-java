package com.github.jntakpe.releasemonitorjava.model;

public class AppVersion {

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppVersion)) return false;

        AppVersion that = (AppVersion) o;

        return raw != null ? raw.equals(that.raw) : that.raw == null;
    }

    @Override
    public int hashCode() {
        return raw != null ? raw.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "AppVersion{" +
                "raw='" + raw + '\'' +
                '}';
    }
}
