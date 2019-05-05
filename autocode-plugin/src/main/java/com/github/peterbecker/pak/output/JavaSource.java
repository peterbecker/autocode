package com.github.peterbecker.pak.output;

import org.apache.maven.project.MavenProject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Represents Java source code, to be compiled as part of the build.
 */
public class JavaSource implements OutputTarget {
    private final Path targetFolder;

    public JavaSource(MavenProject project) throws IOException {
        this.targetFolder = Paths.get(project.getBuild().getDirectory(), "generated-sources", "autocode");
        Files.createDirectories(targetFolder);
        project.addCompileSourceRoot(targetFolder.toString());
    }

    @Override
    public Writer getWriter(String packageName, String fileName) throws IOException {
        Path packageDir = targetFolder.resolve(packageName);
        Files.createDirectories(packageDir);
        Path entityFile = packageDir.resolve(fileName);
        return Files.newBufferedWriter(entityFile);
    }
}
