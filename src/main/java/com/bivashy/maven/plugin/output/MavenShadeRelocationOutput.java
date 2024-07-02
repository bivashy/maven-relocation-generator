package com.bivashy.maven.plugin.output;

import com.bivashy.maven.plugin.jar.JarPackage;
import com.bivashy.maven.plugin.mapper.Mapper;
import com.bivashy.maven.plugin.mojo.ArtifactJarNode;
import org.apache.maven.model.Plugin;
import org.apache.maven.plugin.MojoExecutionException;
import org.codehaus.plexus.logging.Logger;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.twdata.maven.mojoexecutor.MojoExecutor.ExecutionEnvironment;

import java.util.Collection;
import java.util.Collections;

import static org.twdata.maven.mojoexecutor.MojoExecutor.executeMojo;
import static org.twdata.maven.mojoexecutor.MojoExecutor.goal;

public class MavenShadeRelocationOutput implements RelocationOutput {

    private static final String MAVEN_SHADE_PLUGIN_KEY_ID = "org.apache.maven.plugins:maven-shade-plugin";
    private final ExecutionEnvironment executionEnvironment;
    private final Logger logger;
    private final Plugin mavenShadePlugin;
    private Xpp3Dom configuration;

    public MavenShadeRelocationOutput(ExecutionEnvironment executionEnvironment, Logger logger) {
        this.executionEnvironment = executionEnvironment;
        this.logger = logger;
        this.mavenShadePlugin = this.executionEnvironment.getMavenProject().getPlugin(MAVEN_SHADE_PLUGIN_KEY_ID);
        if (mavenShadePlugin == null) {
            logger.error("Maven shade plugin doesn't found!");
            return;
        }

        this.configuration = (Xpp3Dom) mavenShadePlugin.getConfiguration();
        if (!mavenShadePlugin.getExecutions().isEmpty()) {
            logger.warn(
                    "Duplicate maven-shade-plugin execution found! Build may take longer than usual, please make sure that maven-shade-plugin doesn't have " +
                            "any <executions> present, " +
                            "relocation-generator-maven-plugin will automatically execute maven-shade-plugin:shade instead of <executions>");
        }
    }

    @Override
    public void output(Collection<ArtifactJarNode> artifactJarNodes, Mapper mapper) {
        for (ArtifactJarNode artifactJarNode : artifactJarNodes) {
            appendRelocation(artifactJarNode, mapper);
        }
    }

    private void appendRelocation(ArtifactJarNode node, Mapper mapper) {
        String relocationPattern = node.getJarNode().getClearPath().replace(JarPackage.SEPARATOR, ".");

        if (configuration == null)
            configuration = new Xpp3Dom("configuration");

        Xpp3Dom relocations = configuration.getChild("relocations");
        if (relocations == null) {
            relocations = new Xpp3Dom("relocations");
            configuration.addChild(relocations);
        }

        // Append your custom relocations
        Xpp3Dom relocation = new Xpp3Dom("relocation");

        Xpp3Dom pattern = new Xpp3Dom("pattern");
        Xpp3Dom shadedPattern = new Xpp3Dom("shadedPattern");

        pattern.setValue(relocationPattern);
        shadedPattern.setValue(mapper.map(Collections.singleton(node)));

        relocation.addChild(pattern);
        relocation.addChild(shadedPattern);

        relocations.addChild(relocation);
    }

    @Override
    public void close() {
        try {
            executeMojo(
                    mavenShadePlugin,
                    goal("shade"),
                    configuration,
                    executionEnvironment
            );
        } catch (MojoExecutionException e) {
            logger.error("Exception occurred during maven-shade-plugin packaging");
            throw new RuntimeException(e);
        }
    }

}
