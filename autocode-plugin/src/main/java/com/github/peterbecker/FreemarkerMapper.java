package com.github.peterbecker;

import com.github.peterbecker.pak.AutoCodePak;
import freemarker.core.Environment;
import freemarker.template.*;

import java.io.IOException;
import java.util.Map;

public class FreemarkerMapper implements TemplateDirectiveModel {
    private final AutoCodePak pak;

    public FreemarkerMapper(AutoCodePak pak) {
        this.pak = pak;
    }

    @SuppressWarnings("unchecked") // needs to implement unchecked interface method
    @Override
    public void execute(
            Environment environment,
            Map params,
            TemplateModel[] templateModels,
            TemplateDirectiveBody templateDirectiveBody
    ) throws TemplateException, IOException {
        Type type = getType(environment, params);
        String platformType = mapType(environment, type);
        environment.getOut().append(platformType);
    }

    private String mapType(Environment env, Type type) throws TemplateException {
        String result = pak.getTypeMap().get(type);
        if (result == null) {
            throw new TemplateException("The current Pak does not support the type " + type.name().toLowerCase(), env);
        }
        return result;
    }

    private Type getType(Environment environment, Map<String, TemplateModel> params) throws TemplateException {
        try {
            String stringVal = ((TemplateScalarModel) params.get("type")).getAsString();
            try {
                return Type.valueOf(stringVal.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new TemplateException("Unknown type: " + stringVal, environment);
            }
        } catch (ClassCastException e) {
            throw new TemplateException("Map directive must have a string passed to the 'type' parameter", environment);
        }
    }
}
