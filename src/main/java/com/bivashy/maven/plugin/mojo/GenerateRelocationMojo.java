package com.bivashy.maven.plugin.mojo;

import com.bivashy.maven.plugin.filter.Filter;
import com.bivashy.maven.plugin.filter.FilterFactory;
import com.bivashy.maven.plugin.filter.configuration.FilterConfiguration;
import com.bivashy.maven.plugin.jar.JarNode;
import com.bivashy.maven.plugin.jar.JarPackage;
import com.bivashy.maven.plugin.mapper.Mapper;
import com.bivashy.maven.plugin.mapper.MapperFactory;
import com.bivashy.maven.plugin.mapper.configuration.MapperConfiguration;
import com.bivashy.maven.plugin.output.RelocationOutput;
import com.bivashy.maven.plugin.output.RelocationOutputFactory;
import com.bivashy.maven.plugin.output.configuration.RelocationOutputConfiguration;
import javax.inject.Inject;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.BuildPluginManager;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.logging.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static org.twdata.maven.mojoexecutor.MojoExecutor.executionEnvironment;

@Mojo(name = "generate", requiresDependencyResolution = ResolutionScope.RUNTIME)
public class GenerateRelocationMojo extends AbstractMojo {

    @Parameter(defaultValue = "${session}", readonly = true, required = true)
    private MavenSession session;
    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    private MavenProject project;
    @Parameter(defaultValue = "${project.build.sourceEncoding}", required = true)
    private String encoding;
    @Parameter(defaultValue = "${project.build.directory}")
    private File outputDirectory;
    @Parameter(required = true)
    private FilterConfiguration filter;
    @Parameter(required = true)
    private MapperConfiguration mapper;
    @Parameter
    private RelocationOutputConfiguration output;
    @Parameter
    private ArtifactSet artifactSet;
    @Inject
    private Logger logger;
    @Inject
    private BuildPluginManager pluginManager;
    @Inject
    private FilterFactory filterFactory;
    @Inject
    private MapperFactory mapperFactory;
    @Inject
    private RelocationOutputFactory outputFactory;

    public void execute() throws MojoExecutionException {
        output.setEncoding(encoding);
        output.setOutputDirectory(outputDirectory);
        output.setExecutionEnvironment(executionEnvironment(
                project,
                session,
                pluginManager
        ));

        Filter compiledFilter = filterFactory.create(filter);
        Mapper compiledMapper = mapperFactory.create(mapper);
        RelocationOutput compiledOutput = outputFactory.create(output);

        Set<Artifact> artifacts = project.getArtifacts();
        ArtifactSelector selector = new ArtifactSelector(artifactSet);

        for (Artifact artifact : artifacts) {
            File artifactFile = artifact.getFile();
            String artifactType = artifact.getType();
            if (!artifactType.equals("jar")) {
                logger.warn("Artifact '" + artifact + "' have invalid type '" + artifact.getType() + "'");
                continue;
            }
            if (!selector.isSelected(artifact)) {
                logger.info("Skipping '" + artifact + "'");
                continue;
            }
            try {
                JarPackage jarPackage = JarPackage.fromJar(artifactFile);
                Collection<ArtifactJarNode> nodes = compiledFilter.filter(jarPackage)
                        .stream()
                        .map(node -> new ArtifactJarNode(artifact, node))
                        .collect(Collectors.toList());
                logger.info("Relocated packages of artifact '" + artifact + "'");
                nodes.stream()
                        .map(ArtifactJarNode::getJarNode)
                        .map(JarNode::getClearPath)
                        .map(path -> path.replace(JarPackage.SEPARATOR, "."))
                        .forEach(relocatedPackage -> logger.info("Relocated package '" + relocatedPackage + "'"));
                compiledOutput.output(nodes, compiledMapper);
            } catch (IOException e) {
                getLog().error("Cannot open JarInputStream", e);
                throw new MojoExecutionException(e);
            }
        }

        compiledOutput.close();
    }

}
