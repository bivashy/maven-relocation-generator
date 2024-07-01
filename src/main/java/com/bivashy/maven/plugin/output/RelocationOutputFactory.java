package com.bivashy.maven.plugin.output;

import com.bivashy.maven.plugin.output.configuration.RelocationOutputConfiguration;
import javax.inject.Inject;
import org.codehaus.plexus.logging.Logger;

public class RelocationOutputFactory {

    @Inject
    private Logger logger;

    public RelocationOutputFactory() {
    }

    public RelocationOutput create(RelocationOutputConfiguration configuration) {
        if (configuration == null) {
            return RelocationOutput.none();
        }
        if (configuration.isLog()) {
            return new LogRelocationOutput(relocation -> logger.info(relocation));
        }
        if (configuration.getFile() != null) {
            return new FileRelocationOutput(configuration.getOutputDirectory(), configuration.getFile(), configuration.getEncoding());
        }
        if (configuration.isShadePlugin()) {
            return new MavenShadeRelocationOutput(configuration.getExecutionEnvironment(), logger);
        }
        return RelocationOutput.none();
    }

}
