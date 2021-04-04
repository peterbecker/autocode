package com.github.peterbecker;

import com.github.peterbecker.pak.AutoCodePak;
import freemarker.core.Environment;
import freemarker.template.*;

import java.util.Map;

public abstract class FreemarkerMapper implements TemplateDirectiveModel {
    protected final AutoCodePak pak;

    public FreemarkerMapper(AutoCodePak pak) {
        this.pak = pak;
    }

    protected String mapType(Environment env, Type type) throws TemplateException {
        String result = pak.getTypeMap().get(type);
        if (result == null) {
            throw new TemplateException("The current Pak does not support the type " + type.name().toLowerCase(), env);
        }
        return result;
    }

    protected Type getType(Environment environment, String typeParam) throws TemplateException {
        try {
            try {
                return Type.valueOf(typeParam.toUpperCase());
            } catch (IllegalArgumentException e) {
                return Type.REFERENCE;
            }
        } catch (ClassCastException e) {
            throw new TemplateException("Directive must have a string passed to the 'type' parameter", environment);
        }
    }

    protected String wrapIntoSet(String baseType) {
        return String.format(pak.getSetFormat(), baseType);
    }
}
