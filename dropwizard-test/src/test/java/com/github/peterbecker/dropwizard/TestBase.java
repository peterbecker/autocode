package com.github.peterbecker.dropwizard;

import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(DropwizardExtensionsSupport.class)
public abstract class TestBase {
    private static final DropwizardAppExtension<TestConfiguration> DROPWIZARD =
            new DropwizardAppExtension<>(
                    TestApplication.class,
                    ResourceHelpers.resourceFilePath("testConfig.yaml")
            );

    protected TestClient client = createClient();

    protected TestClient createClient() {
        return new TestClient(DROPWIZARD.client(), DROPWIZARD.getLocalPort());
    }
}
