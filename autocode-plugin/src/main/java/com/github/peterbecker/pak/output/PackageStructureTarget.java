package com.github.peterbecker.pak.output;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Represents an output target that will create a nested package structure in the way Java uses it.
 */
public class PackageStructureTarget implements OutputTarget {
    private final Path rootFolder;

    public PackageStructureTarget(Path rootFolder) throws IOException {
        this.rootFolder = rootFolder;
        Files.createDirectories(rootFolder);
    }

    @Override
    public Writer getWriter(String packageName, String fileName, Optional<String> entityName) throws IOException {
        Path packageDir = rootFolder.resolve(packageName);
        Files.createDirectories(packageDir);
        Path targetFile = packageDir.resolve(fileName);
        return Files.newBufferedWriter(targetFile);
    }

    public Path getRootFolder() {
        return rootFolder;
    }
}
