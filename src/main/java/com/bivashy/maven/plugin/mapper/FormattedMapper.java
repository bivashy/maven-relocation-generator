package com.bivashy.maven.plugin.mapper;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.maven.artifact.Artifact;

import com.bivashy.maven.plugin.jar.JarNode;
import com.bivashy.maven.plugin.jar.JarPackage;
import com.bivashy.maven.plugin.mapper.configuration.FormattedMapperConfiguration;
import com.bivashy.maven.plugin.mojo.ArtifactJarNode;

public class FormattedMapper implements Mapper {

    private final FormattedMapperConfiguration configuration;

    public FormattedMapper(FormattedMapperConfiguration configuration) {
        this.configuration = configuration;
    }

    private static Map<String, String> createPlaceholders(ArtifactJarNode node) {
        JarNode jarNode = node.getJarNode();
        Artifact artifact = node.getArtifact();

        return new HashMap<String, String>() {{
            put("{clear-path}", jarNode.getClearPath());
            put("{package-path}", jarNode.getClearPath().replace(JarPackage.SEPARATOR, "."));
            put("{name}", jarNode.getName());
            put("{path}", jarNode.getPath());
            put("{artifact-id}", artifact.getArtifactId());
            put("{group-id}", artifact.getGroupId());
            put("{version}", artifact.getVersion());
            put("{type}", artifact.getType());
            put("{scope}", artifact.getScope());
            put("{id}", artifact.getId());
        }};
    }

    @Override
    public String map(Collection<ArtifactJarNode> jarNodes) {
        return jarNodes.stream().map(node -> {
            String result = configuration.getFormat();
            Map<String, String> placeholders = createPlaceholders(node);
            StringBuilder resultBuilder = new StringBuilder(result);

            for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                int start;
                String key = entry.getKey();
                while ((start = resultBuilder.indexOf(key)) != -1) {
                    resultBuilder.replace(start, start + key.length(), entry.getValue());
                }
            }

            result = resultBuilder.toString();

            return result;
        }).collect(Collectors.joining(configuration.getDelimiter(), configuration.getPrefix(), configuration.getSuffix()));
    }

}
