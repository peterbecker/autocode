package com.github.peterbecker;

import com.github.peterbecker.autocode.entities.EntityType;
import com.github.peterbecker.autocode.entities.PropertyType;
import com.github.peterbecker.pak.AutoCodePak;
import freemarker.core.Environment;
import freemarker.ext.beans.BeanModel;
import freemarker.template.*;

import java.io.IOException;
import java.util.Map;

public class FreemarkerImportsMapper extends FreemarkerMapper {

    public FreemarkerImportsMapper(AutoCodePak pak) {
        super(pak);
    }

    @SuppressWarnings("unchecked") // needs to implement unchecked interface method
    @Override
    public void execute(
            Environment environment,
            Map params,
            TemplateModel[] templateModels,
            TemplateDirectiveBody body
    ) throws TemplateException, IOException {
        EntityType entity = getEntityParam(params);
        boolean hasImport = false;
        for (PropertyType property : entity.getProperty()) {
            Type type = getType(environment, property.getType());
            if (type == Type.REFERENCE) {
                environment.getConfiguration().setSharedVariable("type", property.getType());
                body.render(environment.getOut());
                hasImport = true;
            }
        }
        if(hasImport) {
            environment.getOut().append("\n");
        }
    }

    protected EntityType getEntityParam(Map<String, TemplateModel> params) {
        BeanModel model = (BeanModel) params.get("entity");
        return (EntityType) model.getWrappedObject();
    }
}
