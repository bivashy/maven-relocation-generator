package com.bivashy.maven.plugin.mapper;

import java.util.Collection;

import com.bivashy.maven.plugin.jar.filter.NodeSearchFilter.Result;
import com.bivashy.maven.plugin.mojo.ArtifactJarNode;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

public class GroovyScriptMapper implements Mapper {
    private final Binding binding = new Binding();
    private final Script compiledMapper;

    public GroovyScriptMapper(String rawScript) {
        GroovyShell shell = new GroovyShell(binding);
        this.compiledMapper = shell.parse(rawScript);
        binding.setProperty("Result", Result.class);
    }

    @Override
    public String map(Collection<ArtifactJarNode> jarNodes) {
        binding.setVariable("nodes", jarNodes);
        Object result = compiledMapper.run();
        if (!(result instanceof String))
            throw new RuntimeException("Cannot cast mapper result, it should be string instead of '" + result.getClass().getName() + "'");
        return (String) result;
    }

}
