package com.github.peterbecker;

import com.github.peterbecker.pak.AutoCodePak;
import freemarker.core.Environment;
import freemarker.template.*;

import java.io.IOException;
import java.util.Map;

public class FreemarkerTypeMapper extends FreemarkerMapper {

    public FreemarkerTypeMapper(AutoCodePak pak) {
        super(pak);
    }

    @SuppressWarnings("unchecked") // needs to implement unchecked interface method
    @Override
    public void execute(
            Environment environment,
            Map params,
            TemplateModel[] templateModels,
            TemplateDirectiveBody templateDirectiveBody
    ) throws TemplateException, IOException {
        String typeParam = getTypeParam(params);
        Type type = getType(environment, typeParam);
        if (type == Type.REFERENCE) {
            environment.getOut().append(typeParam);
        } else {
            String platformType = mapType(environment, type);
            environment.getOut().append(platformType);
        }
    }

    protected String getTypeParam(Map<String, TemplateModel> params) throws TemplateModelException {
        return ((TemplateScalarModel) params.get("type")).getAsString();
    }
}
