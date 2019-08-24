package com.github.peterbecker.dropwizard;

import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.ClassRule;

public abstract class TestBase {
    @ClassRule
    public static final DropwizardAppRule<TestConfiguration> RULE =
            new DropwizardAppRule<>(TestApplication.class, ResourceHelpers.resourceFilePath("testConfig.yaml"));

    protected TestClient createClient() {
        return new TestClient(RULE.getEnvironment(), RULE.getLocalPort());
    }
}
