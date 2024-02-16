package com.bivashy.maven.plugin.filter;

import com.bivashy.maven.plugin.filter.configuration.FilterConfiguration;

public class FilterFactory {

    public Filter create(FilterConfiguration configuration) {
        if (configuration.getGroovy() != null)
            return new GroovyScriptFilter(configuration.getGroovy());
        if (configuration.getBasic() != null)
            return new BasicFilter(configuration.getBasic());
        throw new IllegalStateException("FilterConfiguration doesn't have <groovy> or <basic> tag, please add at least one filter.");
    }

}
