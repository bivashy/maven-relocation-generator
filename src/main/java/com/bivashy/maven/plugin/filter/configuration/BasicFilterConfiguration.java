package com.bivashy.maven.plugin.filter.configuration;

import java.util.Set;

public class BasicFilterConfiguration {

    private int minDirectoryCount;
    private int minFileCount;
    private Set<String> excludes;

    public int getMinDirectoryCount() {
        return minDirectoryCount;
    }

    public int getMinFileCount() {
        return minFileCount;
    }

    public Set<String> getExcludes() {
        return excludes;
    }

}
