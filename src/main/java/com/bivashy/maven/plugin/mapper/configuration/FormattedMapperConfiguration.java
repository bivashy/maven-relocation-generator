package com.bivashy.maven.plugin.mapper.configuration;

public class FormattedMapperConfiguration {

    private String format;
    private String delimiter = System.lineSeparator();
    private String prefix = "";
    private String suffix = "";

    public FormattedMapperConfiguration() {
    }

    public FormattedMapperConfiguration(String format, String delimiter, String prefix, String suffix) {
        this.format = format;
        this.delimiter = delimiter;
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public String getFormat() {
        return format;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }

}
