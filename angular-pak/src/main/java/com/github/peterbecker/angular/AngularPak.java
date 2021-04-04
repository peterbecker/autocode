package com.github.peterbecker.angular;

import com.github.peterbecker.Type;
import com.github.peterbecker.Types;
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AngularPak implements AutoCodePak {
    private OutputTarget globalFiles;
    private OutputTarget entityFiles;

    @Override
    public String getName() {
        return "Angular";
    }

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
        return Stream.of(
                "autocode.module.ts",
                "autocode-routing.module.ts",
                "navigation.component.ts",
                "navigation.component.html",
                "navigation.component.css"
        ).map(this::createGlobalTemplate).collect(Collectors.toList());
    }

    @Override
    public List<EntityTemplate> getEntityTemplates() {
        return Stream.of(
                ".module.ts",
                ".model.ts",
                ".service.ts",
                ".resolver.ts",
                ".component.ts",
                ".component.html",
                ".component.css",
                "-list.component.ts",
                "-list.component.html",
                "-list.component.css"
        ).map(this::createEntityTemplate).collect(Collectors.toList());
    }

    private GlobalTemplate createGlobalTemplate(String fileName) {
        return new GlobalTemplate(fileName + ".ftl", globalFiles, fileName);
    }

    private EntityTemplate createEntityTemplate(String fileSuffix) {
        return new EntityTemplate("entity" + fileSuffix + ".ftl", entityFiles, e -> mapEntityName(e) + fileSuffix);
    }

    private String mapEntityName(EntityType e) {
        return e.getName().toLowerCase();
    }

    @Override
    public Map<Type, String> getTypeMap() {
        return Types.TYPE_SCRIPT;
    }

    @Override
    public String getSetFormat() {
        return Types.TYPE_SCRIPT_SET_FORMAT;
    }
}
