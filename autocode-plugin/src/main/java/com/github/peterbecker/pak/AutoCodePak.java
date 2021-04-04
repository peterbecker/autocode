package com.github.peterbecker.pak;

import com.github.peterbecker.Type;
import com.github.peterbecker.pak.templates.EntityTemplate;
import com.github.peterbecker.pak.templates.GlobalTemplate;
import org.apache.maven.project.MavenProject;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface AutoCodePak {
    /**
     * A name used for log output.
     */
    String getName();

    void init(MavenProject project) throws IOException;
    List<GlobalTemplate> getGlobalTemplates();
    List<EntityTemplate> getEntityTemplates();

    /**
     * Maps the AutoCode types to the strings representing the platform specific types.
     */
    Map<Type, String> getTypeMap();

    /**
     * A format suitable to wrap a base type into a set, using a single %s to insert the base type.
     */
    String getSetFormat();
}
