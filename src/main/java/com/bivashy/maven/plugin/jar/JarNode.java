package com.bivashy.maven.plugin.jar;

import static com.bivashy.maven.plugin.jar.JarPackage.SEPARATOR;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.function.Predicate;

import com.bivashy.maven.plugin.jar.filter.NodeSearchFilter;
import com.bivashy.maven.plugin.jar.filter.NodeSearchFilter.Result;

public class JarNode {

    protected Map<String, JarNode> children = new HashMap<>();
    private final String path;
    private final String name;

    JarNode(String path, String name) {
        this.path = path;
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public int nestedLevel() {
        return path.split(SEPARATOR).length - 1;
    }

    public long fileCount() {
        return getChildren().values().stream().filter(node -> !node.hasChildren()).count();
    }

    public long directoryCount() {
        return getChildren().values().stream().filter(JarNode::hasChildren).count();
    }

    public String getName() {
        return name;
    }

    public boolean isRoot() {
        return nestedLevel() == 0;
    }

    public boolean hasChildren() {
        return !children.isEmpty();
    }

    public boolean hasChildren(Predicate<JarNode> filter) {
        return children.values().stream().anyMatch(filter);
    }

    public Set<JarNode> findDeepChildren(NodeSearchFilter filter) {
        Set<JarNode> nodes = new HashSet<>();
        Stack<JarNode> stack = new Stack<>();
        stack.push(this);

        while (!stack.isEmpty()) {
            JarNode current = stack.pop();
            Collection<Result> result = filter.test(current);
            if (result.contains(Result.PASS))
                nodes.add(current);

            if (result.contains(Result.RESOLVE_CHILDREN))
                for (JarNode child : current.getChildren().values()) {
                    stack.push(child);
                }
        }
        return nodes;
    }

    public Map<String, JarNode> getChildren() {
        return Collections.unmodifiableMap(children);
    }

    /**
     * Retrieves path without dot and slash at the start
     * <p>
     * If {@link #getPath()} returns './META-INF/', this method returns 'META-INF'
     *
     * @return path without dot and slash at the start
     */
    public String getClearPath() {
        if (path.length() <= 2)
            return path.substring(1);
        return path.substring(2, path.length() - 1);
    }

}