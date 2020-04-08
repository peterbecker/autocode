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
public class JavaSource extends PackageStructureTarget {
    public JavaSource(MavenProject project) throws IOException {
        super(Paths.get(project.getBuild().getDirectory(), "generated-sources", "autocode"));
        project.addCompileSourceRoot(getRootFolder().toString());
    }
}
