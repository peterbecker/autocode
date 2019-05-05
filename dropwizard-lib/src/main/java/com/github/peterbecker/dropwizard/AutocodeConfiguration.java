package com.github.peterbecker.dropwizard;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class AutocodeConfiguration extends Configuration {
    @NotEmpty
    private String persistenceUnitName;

    @JsonProperty
    public String getPersistenceUnitName() {
        return persistenceUnitName;
    }

    @JsonProperty
    public void setPersistenceUnitName(String persistenceUnitName) {
        this.persistenceUnitName = persistenceUnitName;
    }
}
