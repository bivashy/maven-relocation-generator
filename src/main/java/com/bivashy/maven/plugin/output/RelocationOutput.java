package com.bivashy.maven.plugin.output;

import com.bivashy.maven.plugin.mapper.Mapper;
import com.bivashy.maven.plugin.mojo.ArtifactJarNode;

import java.io.Closeable;
import java.util.Collection;

public interface RelocationOutput extends Closeable {

    static RelocationOutput none() {
        return (artifactJarNodes, mapper) -> {
        };
    }

    void output(Collection<ArtifactJarNode> artifactJarNodes, Mapper mapper);

    @Override
    default void close() {
    }

}
