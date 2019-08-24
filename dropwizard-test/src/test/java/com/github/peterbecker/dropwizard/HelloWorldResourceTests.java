package com.github.peterbecker.dropwizard;

import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;

// Check the custom resource gets wired in.
public class HelloWorldResourceTests extends TestBase {
    @Test
    public void sayHello() {
        TestClient client = createClient();

        Response response = client.get("hello");
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.readEntity(String.class)).isEqualTo("Hello World!");
    }
}
