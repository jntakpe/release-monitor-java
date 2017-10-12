package com.github.jntakpe.releasemonitorjava.utils;

public final class PathUtils {

    public static String dotToSlash(String input) {
        return input.replaceAll("\\.", "/");
    }

    public static String removeLeadingSlash(String input) {
        return input.replaceFirst("/", "");
    }

}
