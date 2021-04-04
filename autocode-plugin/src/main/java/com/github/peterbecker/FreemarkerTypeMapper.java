package com.github.peterbecker;

import com.github.peterbecker.pak.AutoCodePak;
import freemarker.core.Environment;
import freemarker.template.*;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

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
        var baseType = getTypeParam(params);
        var type = getType(environment, baseType);
        if (type != Type.REFERENCE) {
            baseType = mapType(environment, type);
        }
        var linkType = getLinkTypeParam(params);
        if("SET".equals(linkType)) {
            environment.getOut().append(wrapIntoSet(baseType));
        } else {
            environment.getOut().append(baseType);
        }
    }

    protected String getTypeParam(Map<String, TemplateModel> params) throws TemplateModelException {
        return ((TemplateScalarModel) params.get("type")).getAsString();
    }

    protected String getLinkTypeParam(Map<String, TemplateModel> params) throws TemplateModelException {
        var linkType = params.get("linkType");
        if(linkType == null) {
            return "SINGLE";
        }
        return ((TemplateScalarModel) linkType).getAsString();
    }
}
