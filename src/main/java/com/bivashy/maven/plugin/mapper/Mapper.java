package com.bivashy.maven.plugin.mapper;

import java.util.Collection;

import com.bivashy.maven.plugin.mojo.ArtifactJarNode;

public interface Mapper {

    String map(Collection<ArtifactJarNode> jarNodes);

}
