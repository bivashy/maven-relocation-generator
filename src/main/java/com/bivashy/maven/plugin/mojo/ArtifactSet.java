package com.bivashy.maven.plugin.mojo;

import java.util.HashSet;
import java.util.Set;

public class ArtifactSet {
    private Set<String> excludes = new HashSet<>();
    private Set<String> includes = new HashSet<>();

    public Set<String> getExcludes() {
        return excludes;
    }

    public Set<String> getIncludes() {
        return includes;
    }

}
