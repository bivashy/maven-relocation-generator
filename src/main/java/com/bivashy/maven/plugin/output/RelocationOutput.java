package com.bivashy.maven.plugin.output;

import com.bivashy.maven.plugin.mapper.Mapper;
import com.bivashy.maven.plugin.mojo.ArtifactJarNode;

import java.util.Collection;

public interface RelocationOutput {

    static RelocationOutput none() {
        return (artifactJarNodes, mapper) -> {
        };
    }

    void output(Collection<ArtifactJarNode> artifactJarNodes, Mapper mapper);

}
