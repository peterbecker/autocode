package com.github.peterbecker;

import com.github.peterbecker.autocode.entities.Entities;
import com.github.peterbecker.autocode.entities.EntityType;
import com.github.peterbecker.pak.AutoCodePak;
import com.github.peterbecker.pak.templates.EntityTemplate;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

/**
 * @goal generate
 */
public class AutoCodePlugin extends AbstractMojo {

    /**
     * The Maven project representing the whole build.
     *
     * @parameter expression="${project}"
     */
    private MavenProject project;

    /**
     * File containing the entity definition.
     *
     * @parameter expression="${project.basedir}/src/main/autocode/entities.xml"
     */
    private File entitiesFile;

    public void execute() throws MojoExecutionException {
        validateParametersAndInitialize();
        List<AutoCodePak> paks = loadPaks();
        Map<EntityTemplate, Template> templates = setUpTemplates(paks);
        Entities entities = readEntities();
        createSourceFiles(entities, templates);
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
        Entities entities;
        try {
            JAXBContext context = JAXBContext.newInstance(Entities.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            entities = (Entities) unmarshaller.unmarshal(entitiesFile);
        } catch (JAXBException e) {
            throw new MojoExecutionException("Failed to parse " + entitiesFile.getAbsolutePath(), e);
        }
        return entities;
    }

    private Map<EntityTemplate, Template> setUpTemplates(List<AutoCodePak> paks) throws MojoExecutionException {
        Configuration fmConfig = new Configuration();
        fmConfig.setClassForTemplateLoading(AutoCodePlugin.class, "/autocode/templates");

        Map<EntityTemplate, Template> result = new HashMap<>();
        try {
            for (AutoCodePak pak : paks) {
                for (EntityTemplate def : pak.getEntityTemplates()) {
                    result.put(def, fmConfig.getTemplate(def.getTemplateFileName()));
                }
            }
        } catch (IOException e) {
            throw new MojoExecutionException("Cannot read template", e);
        }
        return result;
    }

    private void createSourceFiles(Entities entities, Map<EntityTemplate, Template> templates) throws MojoExecutionException {
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
                getLog().debug("Output file is " + fileName);
                try (Writer out = definition.getOutputTarget().getWriter(packageName, fileName)) {
                    template.process(data, out);
                } catch (TemplateException e) {
                    throw new MojoExecutionException("Can not process template", e);
                } catch (IOException e) {
                    throw new MojoExecutionException("Can not generate code", e);
                }
            }
        }
    }
}
