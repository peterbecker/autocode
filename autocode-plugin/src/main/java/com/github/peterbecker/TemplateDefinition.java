package com.github.peterbecker;


import com.github.peterbecker.autocode.entities.EntityType;
import lombok.Data;

import java.util.function.Function;

@Data
public class TemplateDefinition {
  private final String templateFileName;
  private final Function<EntityType, String> outputFileNameGenerator;
}
