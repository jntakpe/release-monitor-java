package com.github.jntakpe.releasemonitorjava.utils;

import org.junit.Test;

import static com.github.jntakpe.releasemonitorjava.utils.PathUtils.dotToSlash;
import static com.github.jntakpe.releasemonitorjava.utils.PathUtils.removeLeadingSlash;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class PathUtilsTest {

    @Test
    public void dotToSlash_shouldReplaceSingleDot() {
        assertThat(dotToSlash("com.github")).isEqualTo("com/github");
    }

    @Test
    public void dotToSlash_shouldReplaceMultipleDot() {
        assertThat(dotToSlash("com.github.jntakpe")).isEqualTo("com/github/jntakpe");
    }

    @Test
    public void dotToSlash_shouldReplaceNone() {
        assertThat(dotToSlash("com")).isEqualTo("com");
    }

    @Test
    public void removeLeadingSlash_shouldRemoveSlash() {
        assertThat(removeLeadingSlash("/0.1.0")).isEqualTo("0.1.0");
    }

    @Test
    public void removeLeadingSlash_shouldRemoveFirstSlash() {
        assertThat(removeLeadingSlash("/0.1.0/")).isEqualTo("0.1.0/");
    }

    @Test
    public void removeLeadingSlash_shouldDoNothing() {
        assertThat(removeLeadingSlash("0.1.0")).isEqualTo("0.1.0");
    }

}