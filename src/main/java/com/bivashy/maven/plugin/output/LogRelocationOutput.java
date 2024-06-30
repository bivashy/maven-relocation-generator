package com.bivashy.maven.plugin.output;

import com.bivashy.maven.plugin.mapper.Mapper;
import com.bivashy.maven.plugin.mojo.ArtifactJarNode;

import java.util.Collection;
import java.util.function.Consumer;

public class LogRelocationOutput implements RelocationOutput {

    private final Consumer<String> logger;

    public LogRelocationOutput(Consumer<String> logger) {
        this.logger = logger;
    }

    @Override
    public void output(Collection<ArtifactJarNode> artifactJarNodes, Mapper mapper) {
        logger.accept(mapper.map(artifactJarNodes));
    }

}
