package com.bivashy.maven.plugin.jar;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.bivashy.maven.plugin.jar.filter.NodeSearchFilter;

public class JarPackage {

    public static final String SEPARATOR = "/";
    JarNode root = new JarNode("./", "");

    public static JarPackage fromJar(File file) throws IOException {
        JarPackage jarPackage = new JarPackage();
        try (JarFile jarFile = new JarFile(file)) {
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements())
                jarPackage.addPath(entries.nextElement().getName());
        }
        return jarPackage;
    }

    public void addPath(String path) {
        String[] parts = path.split(SEPARATOR);
        JarNode current = root;
        for (String part : parts) {
            current.children.putIfAbsent(part, new JarNode(current.getPath() + part + SEPARATOR, part));
            current = current.children.get(part);
        }
    }

    public Set<JarNode> findNodes(NodeSearchFilter filter) {
        return root.findDeepChildren(filter);
    }

    public JarNode getRoot() {
        return root;
    }

}
