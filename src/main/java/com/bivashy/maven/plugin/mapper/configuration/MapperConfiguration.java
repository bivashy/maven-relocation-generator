package com.bivashy.maven.plugin.mapper.configuration;

public class MapperConfiguration {

    private FormattedMapperConfiguration formatted;
    private String groovy;
    private boolean mavenShadePlugin;

    public FormattedMapperConfiguration getFormatted() {
        return formatted;
    }

    public String getGroovy() {
        return groovy;
    }

    public boolean isMavenShadePlugin() {
        return mavenShadePlugin;
    }

}
