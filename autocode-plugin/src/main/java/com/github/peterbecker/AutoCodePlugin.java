package com.github.peterbecker;

import com.github.peterbecker.autocode.entities.Entities;
import com.github.peterbecker.autocode.entities.EntityType;
import com.github.peterbecker.autocode.entities.PropertyType;
import com.github.peterbecker.pak.AutoCodePak;
import com.github.peterbecker.pak.templates.AutoCodeTemplate;
import com.github.peterbecker.pak.templates.EntityTemplate;
import com.github.peterbecker.pak.templates.GlobalTemplate;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

@Mojo(
        name = "generate",
        defaultPhase = LifecyclePhase.GENERATE_SOURCES
)
public class AutoCodePlugin extends AbstractMojo {
    @Parameter(property = "project")
    private MavenProject project;

    /**
     * File containing the entity definition.
     */
    @Parameter(defaultValue = "${project.basedir}/src/main/autocode/entities.xml")
    private File entitiesFile;

    public void execute() throws MojoExecutionException {
        validateParametersAndInitialize();
        List<AutoCodePak> paks = loadPaks();
        Entities entities = readEntities();
        validateEntities(entities);

        createGlobalSourceFiles(entities, setUpGlobalTemplates(paks));
        createEntitySourceFiles(entities, setUpEntityTemplates(paks));
    }

    private void validateEntities(Entities entities) throws MojoExecutionException {
        for (EntityType entity : entities.getEntity()) {
            for (PropertyType property : entity.getProperty()) {
                try {
                    Type.valueOf(property.getType().toUpperCase());
                } catch (IllegalArgumentException e) {
                    throw new MojoExecutionException("Entity '" + entity.getName() + "' has unknown type '" +
                            property.getType() + "' on property '" + property.getName() + "'");
                }
            }
        }
    }

    private List<AutoCodePak> loadPaks() throws MojoExecutionException {
        try {
            ServiceLoader<AutoCodePak> loader = ServiceLoader.load(AutoCodePak.class);
            List<AutoCodePak> result = new ArrayList<>();
            for (AutoCodePak pak : loader) {
                getLog().info("Loaded Pak " + pak.getClass().getCanonicalName());
                pak.init(project);
                getLog().debug("Initialised Pak " + pak.getClass().getCanonicalName());
                result.add(pak);
            }
            return result;
        } catch (IOException e) {
            throw new MojoExecutionException("Failed to initialize Pak", e);
        }
    }

    private void validateParametersAndInitialize() throws MojoExecutionException {
        if (!entitiesFile.exists()) {
            throw new MojoExecutionException("Can not find input file " + entitiesFile.getAbsolutePath());
        }
    }

    private Entities readEntities() throws MojoExecutionException {
        try {
            JAXBContext context = JAXBContext.newInstance(Entities.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(
                    new StreamSource(
                            AutoCodePlugin.class.getResourceAsStream("/entities.xsd")
                    )
            );
            unmarshaller.setSchema(schema);
            return (Entities) unmarshaller.unmarshal(entitiesFile);
        } catch (JAXBException | SAXException e) {
            throw new MojoExecutionException("Failed to parse " + entitiesFile.getAbsolutePath(), e);
        }
    }

    private Map<EntityTemplate, Template> setUpEntityTemplates(List<AutoCodePak> paks) throws MojoExecutionException {
        Map<EntityTemplate, Template> result = new HashMap<>();
        try {
            for (AutoCodePak pak : paks) {
                getLog().info("Processing entities for Pak: " + pak.getName());
                Configuration fmConfig = createFreemarkerConfig(pak);
                for (EntityTemplate def : pak.getEntityTemplates()) {
                    result.put(def, fmConfig.getTemplate(def.getTemplateFileName()));
                }
            }
        } catch (IOException e) {
            throw new MojoExecutionException("Cannot read template", e);
        }
        return result;
    }

    private Configuration createFreemarkerConfig(AutoCodePak pak) {
        Configuration fmConfig = new Configuration(Configuration.VERSION_2_3_28);
        fmConfig.setClassForTemplateLoading(AutoCodePlugin.class, "/autocode/templates");
        fmConfig.setSharedVariable("map", new FreemarkerMapper(pak));
        return fmConfig;
    }

    private void createEntitySourceFiles(Entities entities, Map<EntityTemplate, Template> templates) throws MojoExecutionException {
        String packageName = entities.getPackage().replace(".", "/");
        getLog().debug("Package is " + packageName);
        Map<String, Object> data = new HashMap<>();
        data.put("package", entities.getPackage());
        for (EntityTemplate definition : templates.keySet()) {
            getLog().debug("Processing definition for template " + definition.getTemplateFileName());
            Template template = templates.get(definition);
            for (EntityType entity : entities.getEntity()) {
                getLog().debug("Processing entity " + entity.getName());
                data.put("entity", entity);
                String fileName = definition.getOutputFileNameGenerator().apply(entity);
                renderTemplate(packageName, data, template, definition, fileName, Optional.of(entity.getName()));
            }
        }
    }

    private Map<GlobalTemplate, Template> setUpGlobalTemplates(List<AutoCodePak> paks) throws MojoExecutionException {
        Map<GlobalTemplate, Template> result = new HashMap<>();
        try {
            for (AutoCodePak pak : paks) {
                getLog().info("Processing global templates for Pak: " + pak.getName());
                Configuration fmConfig = createFreemarkerConfig(pak);
                for (GlobalTemplate def : pak.getGlobalTemplates()) {
                    result.put(def, fmConfig.getTemplate(def.getTemplateFileName()));
                }
            }
        } catch (IOException e) {
            throw new MojoExecutionException("Cannot read template", e);
        }
        return result;
    }

    private void createGlobalSourceFiles(Entities entities, Map<GlobalTemplate, Template> templates) throws MojoExecutionException {
        String packageName = entities.getPackage().replace(".", "/");
        getLog().debug("Package is " + packageName);
        Map<String, Object> data = new HashMap<>();
        data.put("package", entities.getPackage());
        data.put("entities", entities.getEntity());
        for (GlobalTemplate definition : templates.keySet()) {
            getLog().debug("Processing definition for template " + definition.getTemplateFileName());
            Template template = templates.get(definition);
            String fileName = definition.getOutputFileName();
            renderTemplate(packageName, data, template, definition, fileName, Optional.empty());
        }
    }

    private void renderTemplate(
            String packageName,
            Map<String, Object> data,
            Template template,
            AutoCodeTemplate definition,
            String fileName,
            Optional<String> entityName
    ) throws MojoExecutionException {
        getLog().debug("Output file is " + fileName);
        try (Writer out = definition.getOutputTarget().getWriter(packageName, fileName, entityName)) {
            template.process(data, out);
        } catch (TemplateException e) {
            throw new MojoExecutionException("Can not process template", e);
        } catch (IOException e) {
            throw new MojoExecutionException("Can not generate code", e);
        }
    }
}
