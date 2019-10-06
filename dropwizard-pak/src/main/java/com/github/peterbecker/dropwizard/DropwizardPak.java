package com.github.peterbecker.dropwizard;

import com.github.peterbecker.pak.AutoCodePak;
import com.github.peterbecker.pak.output.JavaSource;
import com.github.peterbecker.pak.output.OutputTarget;
import com.github.peterbecker.pak.templates.EntityTemplate;
import com.github.peterbecker.pak.templates.GlobalTemplate;
import org.apache.maven.project.MavenProject;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DropwizardPak implements AutoCodePak {
    private OutputTarget javaSources;

    @Override
    public void init(MavenProject project) throws IOException {
        this.javaSources = new JavaSource(project);
    }

    @Override
    public List<GlobalTemplate> getGlobalTemplates() {
        return Collections.singletonList(
                new GlobalTemplate("AutocodeApplication.java.ftl", javaSources, "AutocodeApplication.java")
        );
    }

    @Override
    public List<EntityTemplate> getEntityTemplates() {
        return Arrays.asList(
                new EntityTemplate("Resource.java.ftl", javaSources, e -> e.getName() + "Resource.java"),
                new EntityTemplate("AppDao.java.ftl", javaSources, e -> e.getName() + "AppDao.java")
        );
    }
}
