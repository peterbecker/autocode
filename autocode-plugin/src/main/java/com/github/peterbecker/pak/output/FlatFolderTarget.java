package com.github.peterbecker.pak.output;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Represents an output target that will put all files into the same folder.
 */
public class FlatFolderTarget implements OutputTarget {
    private final Path rootFolder;

    public FlatFolderTarget(Path rootFolder) throws IOException {
        this.rootFolder = rootFolder;
        Files.createDirectories(rootFolder);
    }

    @Override
    public Writer getWriter(String packageName, String fileName, Optional<String> entityName) throws IOException {
        Path targetFile = rootFolder.resolve(fileName);
        return Files.newBufferedWriter(targetFile);
    }
}
