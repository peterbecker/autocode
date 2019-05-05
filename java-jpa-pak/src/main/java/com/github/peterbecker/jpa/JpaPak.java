package com.github.peterbecker.jpa;

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

public class JpaPak implements AutoCodePak {
    private OutputTarget javaSources;

    @Override
    public void init(MavenProject project) throws IOException {
        this.javaSources = new JavaSource(project);
    }

    @Override
    public List<GlobalTemplate> getGlobalTemplates() {
        return Collections.emptyList();
    }

    @Override
    public List<EntityTemplate> getEntityTemplates() {
        return Arrays.asList(
                new EntityTemplate("Dao.java.ftl", javaSources, e -> e.getName() + "Dao.java"),
                new EntityTemplate("Entity.java.ftl", javaSources, e -> e.getName() + ".java")
        );
    }
}
