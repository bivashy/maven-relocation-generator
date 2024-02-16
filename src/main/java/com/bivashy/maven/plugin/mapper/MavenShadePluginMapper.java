package com.bivashy.maven.plugin.mapper;

import com.bivashy.maven.plugin.mapper.configuration.FormattedMapperConfiguration;

public class MavenShadePluginMapper extends FormattedMapper {

    public MavenShadePluginMapper() {
        super(new FormattedMapperConfiguration(
                "<!-- {group-id}:{artifact-id} -->\n" +
                        "<relocation>\n" +
                        "    <pattern>{package-path}</pattern>\n" +
                        "    <shadedPattern>BASE_PACKAGE.{package-path}</shadedPattern>\n" +
                        "</relocation>",
                System.lineSeparator(), "", ""));
    }

}
