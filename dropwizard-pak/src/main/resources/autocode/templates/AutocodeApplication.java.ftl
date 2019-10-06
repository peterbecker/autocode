<#--noinspection WrongPackageStatement,WeakerAccess-->
package ${package};

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

import com.github.peterbecker.dropwizard.AutocodeApplicationBase;
import com.github.peterbecker.dropwizard.AutocodeConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.google.common.collect.ImmutableList;

import javax.annotation.OverridingMethodsMustInvokeSuper;

public abstract class AutocodeApplication<C extends AutocodeConfiguration> extends AutocodeApplicationBase<C> {
    @Override
    public ImmutableList<Class<?>> getEntities() {
        ImmutableList.Builder<Class<?>> builder = ImmutableList.builder();
    <#list entities as entity>
        builder.add(${entity.name}.class);
    </#list>
        return builder.build();
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    @OverridingMethodsMustInvokeSuper
    public void run(C c, Environment environment) throws Exception {
<#list entities as entity>
        environment.jersey().register(new ${entity.name}Resource(new ${entity.name}AppDao(hibernate.getSessionFactory())));
</#list>
    }
}
