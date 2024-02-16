package com.bivashy.maven.plugin.mojo;

import org.apache.maven.artifact.Artifact;

import com.bivashy.maven.plugin.jar.JarNode;

public class ArtifactJarNode {

    private final Artifact artifact;
    private final JarNode jarNode;

    public ArtifactJarNode(Artifact artifact, JarNode jarNode) {
        this.artifact = artifact;
        this.jarNode = jarNode;
    }

    public Artifact getArtifact() {
        return artifact;
    }

    public JarNode getJarNode() {
        return jarNode;
    }

}
