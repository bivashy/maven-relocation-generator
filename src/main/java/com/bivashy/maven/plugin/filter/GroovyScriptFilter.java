package com.bivashy.maven.plugin.filter;

import java.util.Collection;

import com.bivashy.maven.plugin.jar.JarNode;
import com.bivashy.maven.plugin.jar.JarPackage;
import com.bivashy.maven.plugin.jar.filter.NodeSearchFilter.Result;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

public class GroovyScriptFilter implements Filter {

    private final Binding binding = new Binding();
    private final Script compiledFilter;

    public GroovyScriptFilter(String rawScript) {
        GroovyShell shell = new GroovyShell(binding);
        this.compiledFilter = shell.parse(rawScript);
        binding.setProperty("Result", Result.class);
    }

    @Override
    public Collection<JarNode> filter(JarPackage jarPackage) {
        return jarPackage.findNodes(this::test);
    }

    public Collection<Result> test(JarNode node) {
        binding.setVariable("node", node);
        Object result = compiledFilter.run();
        if (!(result instanceof Collection))
            throw new RuntimeException("Cannot cast filter result, it should be collection/array instead of '" + result.getClass().getName() + "'");
        return (Collection<Result>) result;
    }

}
