package com.github.peterbecker.pak.templates;


import com.github.peterbecker.pak.output.OutputTarget;
import lombok.Data;

/**
 * Template that is applied once for all entities.
 */
@Data
public class GlobalTemplate implements AutoCodeTemplate {
  private final String templateFileName;
  private final OutputTarget outputTarget;
  private final String outputFileName;
}
