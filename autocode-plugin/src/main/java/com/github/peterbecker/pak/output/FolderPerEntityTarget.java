package com.github.peterbecker.pak.output;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Function;

/**
 * Represents an output target that will create a nested package structure in the way Java uses it.
 */
public class FolderPerEntityTarget implements OutputTarget {
    private final Path rootFolder;
    private final Function<String, String> folderNameTransformer;

    public FolderPerEntityTarget(Path rootFolder) throws IOException {
        this(rootFolder, Function.identity());
    }

    public FolderPerEntityTarget(Path rootFolder, Function<String, String> folderNameTransformer) throws IOException {
        this.rootFolder = rootFolder;
        this.folderNameTransformer = folderNameTransformer;
        Files.createDirectories(rootFolder);
    }

    @Override
    public Writer getWriter(String packageName, String fileName, Optional<String> entityName) throws IOException {
        Path targetDir = rootFolder.resolve(folderNameTransformer.apply(entityName.orElseThrow()));
        Files.createDirectories(targetDir);
        Path targetFile = targetDir.resolve(fileName);
        return Files.newBufferedWriter(targetFile);
    }
}
