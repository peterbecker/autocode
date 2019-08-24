package com.github.peterbecker.dropwizard;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/hello")
public class HelloWorldResource {
    private final String message;

    public HelloWorldResource(String message) {
        this.message = message;
    }

    @GET
    public String hello() {
        return message;
    }
}
