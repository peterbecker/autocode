package com.github.peterbecker.jpa;

import com.github.peterbecker.AutoCodePak;
import com.github.peterbecker.TemplateDefinition;
import edu.emory.mathcs.backport.java.util.Arrays;

import java.util.List;

public class JpaPak implements AutoCodePak {
    @Override
    public List<TemplateDefinition> getTemplates() {
        return Arrays.asList(new TemplateDefinition[]{
                new TemplateDefinition("Dao.java.ftl", e -> e.getName() + "Dao.java"),
                new TemplateDefinition("Entity.java.ftl", e -> e.getName() + ".java")
        });
    }
}
