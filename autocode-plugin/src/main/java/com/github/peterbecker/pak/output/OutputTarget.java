package com.github.peterbecker.pak.output;

import java.io.IOException;
import java.io.Writer;

/**
 * Represents an output folder for files.
 */
public interface OutputTarget {
    Writer getWriter(String packageName, String fileName) throws IOException;
}
