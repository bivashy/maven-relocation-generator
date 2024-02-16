package com.bivashy.maven.plugin.mojo;

import java.util.Optional;

import org.apache.maven.artifact.Artifact;
import org.codehaus.plexus.util.SelectorUtils;

public class ArtifactId {

    private final String groupId;
    private final String artifactId;
    private final String type;
    private final String classifier;

    ArtifactId(Artifact artifact) {
        this(artifact.getGroupId(), artifact.getArtifactId(), artifact.getType(), artifact.getClassifier());
    }

    ArtifactId(String groupId, String artifactId, String type, String classifier) {
        this.groupId = Optional.ofNullable(groupId).orElse("");
        this.artifactId = Optional.ofNullable(artifactId).orElse("*");
        this.type = Optional.ofNullable(type).orElse("");
        this.classifier = Optional.ofNullable(classifier).orElse("");
    }

    ArtifactId(String pattern) {
        String[] tokens = new String[0];
        if (pattern != null && !pattern.isEmpty())
            tokens = pattern.split(":");
        groupId = select(tokens, 0).orElse("");
        artifactId = select(tokens, 1).orElse("*");
        type = select(tokens, 2).orElse("*");
        classifier = select(tokens, 3).orElse("*");
    }

    public String getGroupId() {
        return groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public String getType() {
        return type;
    }

    public String getClassifier() {
        return classifier;
    }

    public boolean matches(ArtifactId pattern) {
        if (pattern == null)
            return false;
        if (!match(groupId, pattern.getGroupId()))
            return false;
        if (!match(artifactId, pattern.getArtifactId()))
            return false;
        if (!match(type, pattern.getType()))
            return false;
        return match(classifier, pattern.getClassifier());
    }

    private Optional<String> select(String[] tokens, int index) {
        if (tokens.length > index)
            return Optional.of(tokens[index]);
        return Optional.empty();
    }

    private boolean match(String id, String pattern) {
        return SelectorUtils.match(pattern, id);
    }

}
