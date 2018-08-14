package com.github.peterbecker;

import com.github.peterbecker.autocode.entities.Entities;
import com.github.peterbecker.autocode.entities.EntityType;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
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
     * Output directory for generated source files.
     *
     * @parameter expression="${project.build.directory}/generated-sources/autocode"
     */
    private File outputDirectory;

    /**
     * File containing the entity definition.
     *
     * @parameter expression="${project.basedir}/src/main/autocode/entities.xml"
     */
    private File entitiesFile;

    public void execute() throws MojoExecutionException {
        try {
            validateParametersAndInitialize();
            List<AutoCodePak> paks = loadPaks();
            Map<TemplateDefinition, Template> templates = setUpTemplates(paks);
            Entities entities = readEntities();
            createSourceFiles(entities, templates);
        } catch (MojoExecutionException e) {
            getLog().error(e);
            throw e;
        }
        project.addCompileSourceRoot(outputDirectory.getAbsolutePath());
    }

    private List<AutoCodePak> loadPaks() {
        ServiceLoader<AutoCodePak> loader = ServiceLoader.load(AutoCodePak.class);
        List<AutoCodePak> result = new ArrayList<>();
        for (AutoCodePak pak: loader) {
            getLog().info("Loaded Pak " + pak.getClass().getCanonicalName());
            result.add(pak);
        }
        return result;
    }

    private void validateParametersAndInitialize() throws MojoExecutionException {
        if (!outputDirectory.exists()) {
            outputDirectory.mkdirs();
        }
        if(!entitiesFile.exists()) {
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

    private Map<TemplateDefinition, Template> setUpTemplates(List<AutoCodePak> paks) throws MojoExecutionException {
        Configuration fmConfig = new Configuration();
        fmConfig.setClassForTemplateLoading(AutoCodePlugin.class, "/autocode/templates");

        Map<TemplateDefinition, Template> result = new HashMap<>();
        try {
            for (AutoCodePak pak : paks) {
                for (TemplateDefinition def : pak.getTemplates()) {
                    result.put(def, fmConfig.getTemplate(def.getTemplateFileName()));
                }
            }
        } catch (IOException e) {
            throw new MojoExecutionException("Cannot read template", e);
        }
        return result;
    }

    private void createSourceFiles(Entities entities, Map<TemplateDefinition, Template> templates) throws MojoExecutionException {
        File packageDirectory = new File(outputDirectory, entities.getPackage().replace(".","/"));
        packageDirectory.mkdirs();
        Map<String, Object> data = new HashMap<>();
        data.put("package", entities.getPackage());
        for (TemplateDefinition definition : templates.keySet()) {
            Template template = templates.get(definition);
            for (EntityType entity : entities.getEntity()) {
                data.put("entity", entity);
                File entityFile = new File(packageDirectory, definition.getOutputFileNameGenerator().apply(entity));
                processTemplate(data, template, entityFile);
            }
        }
    }

    private void processTemplate(Map<String, Object> data, Template template, File entityFile) throws MojoExecutionException {
        Writer out = null;
        try {
            out = new OutputStreamWriter(new FileOutputStream(entityFile));
            template.process(data, out);
        } catch (TemplateException e) {
            throw new MojoExecutionException("Can not process template", e);
        } catch (IOException e) {
            throw new MojoExecutionException("Can not generate code at " + entityFile.getAbsolutePath(), e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    throw new MojoExecutionException("Failed to close output.", e);
                }
            }
        }
    }
}
