package com.github.peterbecker;

import com.github.peterbecker.autocode.entities.Entities;
import com.github.peterbecker.autocode.entities.EntityType;
import com.github.peterbecker.autocode.entities.LinkType;
import com.github.peterbecker.autocode.entities.PropertyType;
import org.junit.Test;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class XsdTest {
    @Test
    public void loadValidFile() throws Exception {
        var context = JAXBContext.newInstance(Entities.class);
        var unmarshaller = context.createUnmarshaller();
        var schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        var schema = schemaFactory.newSchema(
                new StreamSource(
                        XsdTest.class.getResourceAsStream("/entities.xsd")
                )
        );
        unmarshaller.setSchema(schema);
        var entities = (Entities) unmarshaller.unmarshal(
                XsdTest.class.getResourceAsStream("/valid.xml")
        );
        assertThat(entities.getEntity()).hasSize(2);
        var personEntity = getEntity(entities, "Person");
        var nameProp = getProperty(personEntity, "name");
        assertThat(nameProp.getLinkType()).isEqualTo(LinkType.SINGLE);
        var rolesProp = getProperty(personEntity, "roles");
        assertThat(rolesProp.getLinkType()).isEqualTo(LinkType.SET);
    }

    private PropertyType getProperty(EntityType personEntity, String name) {
        return personEntity.getProperty().stream().filter(p -> p.getName().equals(name)).findAny().orElseThrow();
    }

    private EntityType getEntity(Entities entities, String name) {
        return entities.getEntity().stream().filter(e -> e.getName().equals(name)).findAny().orElseThrow();
    }
}
