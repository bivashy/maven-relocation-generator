package com.bivashy.maven.plugin.output.configuration;

import org.twdata.maven.mojoexecutor.MojoExecutor.ExecutionEnvironment;

import java.io.File;

public class RelocationOutputConfiguration {

    private boolean log;
    private String file;
    private boolean shadePlugin;
    private String encoding = "UTF-8";
    private File outputDirectory;
    private ExecutionEnvironment executionEnvironment;

    public boolean isLog() {
        return log;
    }

    public String getFile() {
        return file;
    }

    public boolean isShadePlugin() {
        return shadePlugin;
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

    public ExecutionEnvironment getExecutionEnvironment() {
        return executionEnvironment;
    }

    public void setExecutionEnvironment(ExecutionEnvironment executionEnvironment) {
        this.executionEnvironment = executionEnvironment;
    }

}
