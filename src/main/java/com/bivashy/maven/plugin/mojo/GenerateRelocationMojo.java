package com.bivashy.maven.plugin.mojo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

import com.bivashy.maven.plugin.filter.Filter;
import com.bivashy.maven.plugin.filter.FilterFactory;
import com.bivashy.maven.plugin.filter.configuration.FilterConfiguration;
import com.bivashy.maven.plugin.jar.JarPackage;
import com.bivashy.maven.plugin.mapper.Mapper;
import com.bivashy.maven.plugin.mapper.MapperFactory;
import com.bivashy.maven.plugin.mapper.configuration.MapperConfiguration;

@Mojo(name = "generate", requiresDependencyResolution = ResolutionScope.COMPILE)
public class GenerateRelocationMojo extends AbstractMojo {

    @Parameter(defaultValue = "${session}", readonly = true, required = true)
    private MavenSession session;
    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    private MavenProject project;
    @Parameter(required = true)
    private FilterConfiguration filter;
    @Parameter(required = true)
    private MapperConfiguration mapper;
    @Parameter
    private ArtifactSet artifactSet;
    @Inject
    private FilterFactory filterFactory;
    @Inject
    private MapperFactory mapperFactory;

    public void execute() throws MojoExecutionException {
        Filter compiledFilter = filterFactory.create(filter);
        Mapper compiledMapper = mapperFactory.create(mapper);

        Set<Artifact> artifacts = project.getArtifacts();
        ArtifactSelector selector = new ArtifactSelector(artifactSet);

        List<String> output = new ArrayList<>();
        for (Artifact artifact : artifacts) {
            File artifactFile = artifact.getFile();
            String artifactType = artifact.getType();
            if (!artifactType.equals("jar"))
                continue;
            if (!selector.isSelected(artifact))
                continue;
            try {
                JarPackage jarPackage = JarPackage.fromJar(artifactFile);
                Collection<ArtifactJarNode> nodes = compiledFilter.filter(jarPackage)
                        .stream()
                        .map(node -> new ArtifactJarNode(artifact, node))
                        .collect(Collectors.toList());
                output.add(compiledMapper.map(nodes));
            } catch (IOException e) {
                getLog().error("Cannot open JarInputStream", e);
                throw new MojoExecutionException(e);
            }
        }
        getLog().info(output.stream().collect(Collectors.joining(System.lineSeparator())));
    }

}
