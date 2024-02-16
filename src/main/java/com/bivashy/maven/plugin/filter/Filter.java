package com.bivashy.maven.plugin.filter;

import java.util.Collection;

import com.bivashy.maven.plugin.jar.JarNode;
import com.bivashy.maven.plugin.jar.JarPackage;

public interface Filter {

    Collection<JarNode> filter(JarPackage jarPackage);

}
