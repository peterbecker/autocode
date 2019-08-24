<#--noinspection HtmlUnknownTag,HtmlMissingClosingTag-->
package ${package};

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

import com.github.peterbecker.dropwizard.AutocodeConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class AutocodeApplication<C extends AutocodeConfiguration> extends Application<C> {
    /**
     * Configures the application with all resources, add customization into @linkplain{#customize(C, Environment)}
     */
    @Override
    public final void run(C c, Environment environment) throws Exception {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(c.getPersistenceUnitName());
        EntityManager em = factory.createEntityManager();
<#list entities as entity>
        environment.jersey().register(new ${entity.name}Resource(new ${entity.name}Dao(em)));
</#list>
        customize(c, environment);
    }

    protected void customize(C c, Environment environment) throws Exception {
        // can be used to further customize startup
    }
}
