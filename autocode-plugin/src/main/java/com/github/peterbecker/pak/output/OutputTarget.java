package com.github.peterbecker.pak.output;

import java.io.IOException;
import java.io.Writer;
import java.util.Optional;

/**
 * Represents an output folder for files.
 */
public interface OutputTarget {
    Writer getWriter(String packageName, String fileName, Optional<String> entityName) throws IOException;
}
