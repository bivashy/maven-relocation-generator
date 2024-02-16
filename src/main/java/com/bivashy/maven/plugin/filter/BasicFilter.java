package com.bivashy.maven.plugin.filter;

import java.util.Collection;
import java.util.Collections;

import com.bivashy.maven.plugin.filter.configuration.BasicFilterConfiguration;
import com.bivashy.maven.plugin.jar.JarNode;
import com.bivashy.maven.plugin.jar.JarPackage;
import com.bivashy.maven.plugin.jar.filter.NodeSearchFilter.Result;

public class BasicFilter implements Filter {

    private final BasicFilterConfiguration configuration;

    public BasicFilter(BasicFilterConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Collection<JarNode> filter(JarPackage jarPackage) {
        return jarPackage.findNodes(node -> {
            if (node.isRoot())
                return Collections.singletonList(Result.RESOLVE_CHILDREN);

            if (!node.hasChildren())
                return Collections.emptyList();
            if (configuration.getExcludes().contains(node.getClearPath()))
                return Collections.emptyList();

            long fileCount = node.fileCount();
            long directoryCount = node.directoryCount();

            return directoryCount > configuration.getMinDirectoryCount() || fileCount > configuration.getMinFileCount() ?
                    Collections.singletonList(Result.PASS) : Collections.singletonList(Result.RESOLVE_CHILDREN);
        });
    }

}
