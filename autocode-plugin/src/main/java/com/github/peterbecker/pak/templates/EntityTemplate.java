package com.github.peterbecker.pak.templates;


import com.github.peterbecker.autocode.entities.EntityType;
import com.github.peterbecker.pak.output.OutputTarget;
import lombok.Data;

import java.util.function.Function;

/**
 * Template which is applied to each entity.
 */
@Data
public class EntityTemplate implements AutoCodeTemplate {
  private final String templateFileName;
  private final OutputTarget outputTarget;
  private final Function<EntityType, String> outputFileNameGenerator;
}
