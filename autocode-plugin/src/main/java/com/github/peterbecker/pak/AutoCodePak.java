package com.github.peterbecker.pak;

import com.github.peterbecker.pak.templates.EntityTemplate;
import com.github.peterbecker.pak.templates.GlobalTemplate;
import org.apache.maven.project.MavenProject;

import java.io.IOException;
import java.util.List;

public interface AutoCodePak {
    void init(MavenProject project) throws IOException;
    List<GlobalTemplate> getGlobalTemplates();
    List<EntityTemplate> getEntityTemplates();
}
