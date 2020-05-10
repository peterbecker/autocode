package com.github.peterbecker;

import com.github.peterbecker.autocode.AutocodeApplication;
import io.dropwizard.setup.Environment;

public class AutocodeTestApplication extends AutocodeApplication<AutocodeTestConfiguration> {
    public static void main(String[] args) throws Exception {
        new AutocodeTestApplication().run(args);
    }

    @Override
    public void run(AutocodeTestConfiguration testConfiguration, Environment environment) throws Exception {
        super.run(testConfiguration, environment);
    }
}
