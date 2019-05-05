package com.github.peterbecker.pak.output;

import org.apache.maven.project.MavenProject;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Represents resources to be available on the classpath.
 */
public class Resources implements OutputTarget {
    private Path targetFolder;

    public Resources(MavenProject project) throws IOException {
        this.targetFolder = Paths.get(project.getBuild().getOutputDirectory(), "generated-resources", "autocode");
        Files.createDirectory(targetFolder);
    }

    @Override
    public Writer getWriter(String packageName, String fileName) {
        return null;
    }
}
