package com.github.peterbecker.dropwizard;

import org.hibernate.validator.constraints.NotEmpty;

public class TestConfiguration extends AutocodeConfiguration {
    @NotEmpty
    private String message;

    public String getMessage() {
        return message;
    }
}
