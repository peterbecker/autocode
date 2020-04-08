package com.github.peterbecker.dropwizard;

import com.github.peterbecker.autocode.entities.EntityType;
import com.github.peterbecker.pak.AutoCodePak;
import com.github.peterbecker.pak.output.FlatFolderTarget;
import com.github.peterbecker.pak.output.FolderPerEntityTarget;
import com.github.peterbecker.pak.output.OutputTarget;
import com.github.peterbecker.pak.templates.EntityTemplate;
import com.github.peterbecker.pak.templates.GlobalTemplate;
import org.apache.maven.project.MavenProject;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class AngularPak implements AutoCodePak {
    private OutputTarget globalFiles;
    private OutputTarget entityFiles;

    @Override
    public void init(MavenProject project) throws IOException {
        this.globalFiles =
                new FlatFolderTarget(
                        Paths.get(project.getBuild().getDirectory(), "generated-sources", "autocode")
                );
        this.entityFiles =
                new FolderPerEntityTarget(
                        Paths.get(project.getBuild().getDirectory(), "generated-sources", "autocode"),
                        String::toLowerCase
                );
    }

    @Override
    public List<GlobalTemplate> getGlobalTemplates() {
        return Arrays.asList(
                new GlobalTemplate("navigation.component.ts.ftl", globalFiles, "navigation.component.ts"),
                new GlobalTemplate("navigation.component.html.ftl", globalFiles, "navigation.component.html"),
                new GlobalTemplate("navigation.component.css.ftl", globalFiles, "navigation.component.css")
        );
    }

    @Override
    public List<EntityTemplate> getEntityTemplates() {
        return Arrays.asList(
                new EntityTemplate("entity.model.ts.ftl", entityFiles, e -> mapEntityName(e) + ".model.ts"),
                new EntityTemplate("entity.service.ts.ftl", entityFiles, e -> mapEntityName(e) + ".service.ts"),
                new EntityTemplate("entity.component.ts.ftl", entityFiles, e -> mapEntityName(e) + ".component.ts"),
                new EntityTemplate("entity.component.html.ftl", entityFiles, e -> mapEntityName(e) + ".component.html"),
                new EntityTemplate("entity.component.css.ftl", entityFiles, e -> mapEntityName(e) + ".component.css"),
                new EntityTemplate("entity-list.component.ts.ftl", entityFiles, e -> mapEntityName(e) + "-list.component.ts"),
                new EntityTemplate("entity-list.component.html.ftl", entityFiles, e -> mapEntityName(e) + "-list.component.html"),
                new EntityTemplate("entity-list.component.css.ftl", entityFiles, e -> mapEntityName(e) + "-list.component.css")
        );
    }

    private String mapEntityName(EntityType e) {
        return e.getName().toLowerCase();
    }
}
