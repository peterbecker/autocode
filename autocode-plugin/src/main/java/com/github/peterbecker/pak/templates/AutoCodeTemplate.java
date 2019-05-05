package com.github.peterbecker.pak.templates;

import com.github.peterbecker.pak.output.OutputTarget;

/**
 * Defines a template to be processed.
 */
public interface AutoCodeTemplate {
    String getTemplateFileName();
    OutputTarget getOutputTarget();
}
