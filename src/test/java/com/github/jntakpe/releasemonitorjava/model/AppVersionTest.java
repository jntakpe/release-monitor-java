package com.github.jntakpe.releasemonitorjava.model;

import org.junit.Test;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

public class AppVersionTest {

    @Test
    public void compareToShouldBeZeroWhenSameVersions() {
        Supplier<AppVersion> supplier =
                () -> new AppVersion().setRaw("1.2.3").setMajor(1).setMinor(2).setPatch(3).setType(VersionType.RELEASE);
        assertThat(supplier.get().compareTo(supplier.get())).isZero();
    }

    @Test
    public void compareToShouldBeZeroWhenSameVersionsButRawChanges() {
        Supplier<AppVersion> supplier =
                () -> new AppVersion().setRaw("1.2.3").setMajor(1).setMinor(2).setPatch(3).setType(VersionType.RELEASE);
        assertThat(supplier.get().compareTo(supplier.get().setRaw("1.2.3-RELEASE"))).isZero();
    }

    @Test
    public void compareToShouldBePositiveWhenMajorGreater() {
        Supplier<AppVersion> supplier =
                () -> new AppVersion().setRaw("2.2.3").setMajor(2).setMinor(2).setPatch(3).setType(VersionType.RELEASE);
        assertThat(supplier.get().compareTo(supplier.get().setRaw("1.2.3").setMajor(1))).isPositive();
    }

    @Test
    public void compareToShouldBeNegativeWhenMajorLeaser() {
        Supplier<AppVersion> supplier =
                () -> new AppVersion().setRaw("1.2.3").setMajor(1).setMinor(2).setPatch(3).setType(VersionType.RELEASE);
        assertThat(supplier.get().compareTo(supplier.get().setRaw("2.2.3").setMajor(2))).isNegative();
    }

    @Test
    public void compareToShouldBePositiveWhenMinorGreater() {
        Supplier<AppVersion> supplier =
                () -> new AppVersion().setRaw("1.3.3").setMajor(1).setMinor(3).setPatch(3).setType(VersionType.RELEASE);
        assertThat(supplier.get().compareTo(supplier.get().setRaw("1.2.3").setMinor(2))).isPositive();
    }

    @Test
    public void compareToShouldBeNegativeWhenMinorLeaser() {
        Supplier<AppVersion> supplier =
                () -> new AppVersion().setRaw("1.1.3").setMajor(1).setMinor(1).setPatch(3).setType(VersionType.RELEASE);
        assertThat(supplier.get().compareTo(supplier.get().setRaw("2.2.3").setMinor(2))).isNegative();
    }

    @Test
    public void compareToShouldBePositiveWhenPatchGreater() {
        Supplier<AppVersion> supplier =
                () -> new AppVersion().setRaw("1.2.4").setMajor(1).setMinor(2).setPatch(4).setType(VersionType.RELEASE);
        assertThat(supplier.get().compareTo(supplier.get().setRaw("1.2.3").setPatch(3))).isPositive();
    }

    @Test
    public void compareToShouldBeNegativeWhenPatchLeaser() {
        Supplier<AppVersion> supplier =
                () -> new AppVersion().setRaw("1.2.2").setMajor(1).setMinor(2).setPatch(2).setType(VersionType.RELEASE);
        assertThat(supplier.get().compareTo(supplier.get().setRaw("1.2.3").setPatch(3))).isNegative();
    }

    @Test
    public void compareToShouldBePositiveWhenReleaseComparingToSnapshot() {
        Supplier<AppVersion> supplier =
                () -> new AppVersion().setRaw("1.2.3").setMajor(1).setMinor(2).setPatch(3).setType(VersionType.RELEASE);
        assertThat(supplier.get().compareTo(supplier.get().setRaw("1.2.3").setType(VersionType.SNAPSHOT))).isPositive();
    }

    @Test
    public void compareToShouldBePositiveWhenReleaseComparingToRC() {
        Supplier<AppVersion> supplier =
                () -> new AppVersion().setRaw("1.2.3").setMajor(1).setMinor(2).setPatch(3).setType(VersionType.RELEASE);
        assertThat(supplier.get().compareTo(supplier.get().setRaw("1.2.3").setType(VersionType.RELEASE_CANDIDATE))).isPositive();
    }

    @Test
    public void compareToShouldBeNegativeWhenSnapshotComparingToRC() {
        Supplier<AppVersion> supplier =
                () -> new AppVersion().setRaw("1.2.3").setMajor(1).setMinor(2).setPatch(3).setType(VersionType.SNAPSHOT);
        assertThat(supplier.get().compareTo(supplier.get().setRaw("1.2.3").setType(VersionType.RELEASE_CANDIDATE))).isNegative();
    }

    @Test
    public void compareToShouldBeNegativeWhenSnapshotComparingToRelease() {
        Supplier<AppVersion> supplier =
                () -> new AppVersion().setRaw("1.2.3").setMajor(1).setMinor(2).setPatch(3).setType(VersionType.SNAPSHOT);
        assertThat(supplier.get().compareTo(supplier.get().setRaw("1.2.3").setType(VersionType.RELEASE))).isNegative();
    }

    @Test
    public void compareToShouldBePositiveWhenRCComparingToSnapshot() {
        Supplier<AppVersion> supplier =
                () -> new AppVersion().setRaw("1.2.3").setMajor(1).setMinor(2).setPatch(3).setType(VersionType.RELEASE_CANDIDATE);
        assertThat(supplier.get().compareTo(supplier.get().setRaw("1.2.3").setType(VersionType.SNAPSHOT))).isPositive();
    }

    @Test
    public void compareToShouldBeNegativeWhenRCComparingToRelease() {
        Supplier<AppVersion> supplier =
                () -> new AppVersion().setRaw("1.2.3").setMajor(1).setMinor(2).setPatch(3).setType(VersionType.RELEASE_CANDIDATE);
        assertThat(supplier.get().compareTo(supplier.get().setRaw("1.2.3").setType(VersionType.RELEASE))).isNegative();
    }

    @Test
    public void compareShouldBeEqualsWhenRCHasSameNumber() {
        Supplier<AppVersion> supplier = () -> new AppVersion()
                .setRaw("1.2.3-RC1").setMajor(1).setMinor(2).setPatch(3).setType(VersionType.RELEASE_CANDIDATE).setRcNumber(1);
        assertThat(supplier.get().compareTo(supplier.get())).isZero();
    }

    @Test
    public void compareShouldBePositiveWhenRCNumberGreater() {
        Supplier<AppVersion> supplier = () -> new AppVersion()
                .setRaw("1.2.3-RC3").setMajor(1).setMinor(2).setPatch(3).setType(VersionType.RELEASE_CANDIDATE).setRcNumber(3);
        assertThat(supplier.get().compareTo(supplier.get().setRaw("1.2.3-RC1").setRcNumber(1))).isPositive();
    }

    @Test
    public void compareShouldBeNegativeWhenRCNumberLeaser() {
        Supplier<AppVersion> supplier = () -> new AppVersion()
                .setRaw("1.2.3-RC1").setMajor(1).setMinor(2).setPatch(3).setType(VersionType.RELEASE_CANDIDATE).setRcNumber(1);
        assertThat(supplier.get().compareTo(supplier.get().setRaw("1.2.3-RC3").setRcNumber(3))).isNegative();
    }

}