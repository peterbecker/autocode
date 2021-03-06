package com.github.peterbecker.dropwizard;

import com.github.peterbecker.autocode.AutocodeApplication;
import io.dropwizard.setup.Environment;

public class TestApplication extends AutocodeApplication<TestConfiguration> {
    public static void main(String[] args) throws Exception {
        new TestApplication().run(args);
    }

    @Override
    public void run(TestConfiguration testConfiguration, Environment environment) throws Exception {
        super.run(testConfiguration, environment);
        environment.jersey().register(new HelloWorldResource(testConfiguration.getMessage()));
    }
}
