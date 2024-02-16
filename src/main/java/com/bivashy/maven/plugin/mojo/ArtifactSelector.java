package com.bivashy.maven.plugin.mojo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.apache.maven.artifact.Artifact;

public class ArtifactSelector {

    private final Collection<ArtifactId> includes;
    private final Collection<ArtifactId> excludes;

    ArtifactSelector(ArtifactSet artifactSet) {
        if (artifactSet == null) {
            this.includes = new ArrayList<>();
            this.excludes = new ArrayList<>();
            return;
        }
        this.includes = artifactSet.getIncludes().stream().map(ArtifactId::new).collect(Collectors.toList());
        this.excludes = artifactSet.getExcludes().stream().map(ArtifactId::new).collect(Collectors.toList());
    }

    public boolean isSelected(Artifact artifact) {
        return artifact != null && isSelected(new ArtifactId(artifact));
    }

    private boolean isSelected(ArtifactId artifactId) {
        return includes.isEmpty() || matches(includes, artifactId) && noneMatches(excludes, artifactId);
    }

    private boolean matches(Collection<ArtifactId> patterns, ArtifactId id) {
        return patterns.stream().anyMatch(id::matches);
    }

    private boolean noneMatches(Collection<ArtifactId> patterns, ArtifactId id) {
        return patterns.stream().noneMatch(id::matches);
    }

}
