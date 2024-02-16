package com.bivashy.maven.plugin.mapper;

import com.bivashy.maven.plugin.mapper.configuration.MapperConfiguration;

public class MapperFactory {

    public Mapper create(MapperConfiguration configuration) {
        if (configuration.getFormatted() != null)
            return new FormattedMapper(configuration.getFormatted());
        if (configuration.getGroovy() != null)
            return new GroovyScriptMapper(configuration.getGroovy());
        if (configuration.isMavenShadePlugin())
            return new MavenShadePluginMapper();
        throw new IllegalStateException("You should define at least one mapper!");
    }

}
