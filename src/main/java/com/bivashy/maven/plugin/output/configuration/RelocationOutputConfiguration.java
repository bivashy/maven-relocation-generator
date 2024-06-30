package com.bivashy.maven.plugin.output.configuration;

import java.io.File;

public class RelocationOutputConfiguration {

    private boolean log;
    private String file;
    private String encoding = "UTF-8";
    private File outputDirectory;

    public boolean isLog() {
        return log;
    }

    public String getFile() {
        return file;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public File getOutputDirectory() {
        return outputDirectory;
    }

    public void setOutputDirectory(File outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

}
