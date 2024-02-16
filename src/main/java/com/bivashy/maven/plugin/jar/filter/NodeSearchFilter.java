package com.bivashy.maven.plugin.jar.filter;

import java.util.Collection;

import com.bivashy.maven.plugin.jar.JarNode;

public interface NodeSearchFilter {

    Collection<Result> test(JarNode node);

    enum Result {
        RESOLVE_CHILDREN, PASS
    }

}
