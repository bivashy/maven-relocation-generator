package com.bivashy.maven.plugin.filter.configuration;

public class FilterConfiguration {

    private String groovy;
    private BasicFilterConfiguration basic;

    public FilterConfiguration() {
    }

    public FilterConfiguration(String groovy, BasicFilterConfiguration basic) {
        this.groovy = groovy;
        this.basic = basic;
    }

    public String getGroovy() {
        return groovy;
    }

    public BasicFilterConfiguration getBasic() {
        return basic;
    }

}
