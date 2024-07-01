package com.bivashy.maven.plugin.output;

import com.bivashy.maven.plugin.mapper.Mapper;
import com.bivashy.maven.plugin.mojo.ArtifactJarNode;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Collection;

public class FileRelocationOutput implements RelocationOutput {

    private final File outputFile;
    private final Charset encoding;
    private BufferedWriter writer;

    public FileRelocationOutput(File outputDirectory, String file, String encoding) {
        this.outputFile = new File(outputDirectory, file);
        this.encoding = Charset.forName(encoding);
    }

    @Override
    public void output(Collection<ArtifactJarNode> artifactJarNodes, Mapper mapper) {
        try {
            outputToFile(artifactJarNodes, mapper);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void outputToFile(Collection<ArtifactJarNode> artifactJarNodes, Mapper mapper) throws IOException {
        if (writer == null)
            this.writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile, true), encoding));
        writer.write(mapper.map(artifactJarNodes));
        writer.newLine();
    }

    @Override
    public void close() {
        try {
            this.writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
