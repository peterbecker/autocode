package com.github.peterbecker.dropwizard;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.ImmutableList;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.SessionFactoryFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import javax.annotation.OverridingMethodsMustInvokeSuper;

/**
 * Base class to be used in combination with generated code, do not subclass directly, subclass the generated
 * AutocodeApplication class instead.
 */
public abstract class AutocodeApplicationBase<C extends AutocodeConfiguration> extends Application<C> {
    protected final HibernateBundle<C> hibernate = new HibernateBundle<C>(getEntities(), new SessionFactoryFactory()) {
        @Override
        public DataSourceFactory getDataSourceFactory(C configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    protected abstract ImmutableList<Class<?>> getEntities();

    @Override
    @OverridingMethodsMustInvokeSuper
    public void run(C config, Environment environment) throws Exception {
        environment.getObjectMapper().configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    public final void initialize(Bootstrap<C> bootstrap) {
        bootstrap.addBundle(hibernate);
    }
}
