package com.bivashy.maven.plugin.mapper.configuration;

public class MapperConfiguration {

    private FormattedMapperConfiguration formatted;
    private String groovy;
    private boolean mavenShadePlugin;

    public MapperConfiguration() {
    }

    public MapperConfiguration(FormattedMapperConfiguration formatted, String groovy, boolean mavenShadePlugin) {
        this.formatted = formatted;
        this.groovy = groovy;
        this.mavenShadePlugin = mavenShadePlugin;
    }

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
