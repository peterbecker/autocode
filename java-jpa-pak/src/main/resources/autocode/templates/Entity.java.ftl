package ${package};

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ${entity.name} {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

<#list entity.property as property>
    private <@map type=property.type/> ${property.name};

</#list>
    public ${entity.name}() {
    }

    public ${entity.name}(<#list entity.property as property><@map type=property.type/> ${property.name}<#if property_has_next>, </#if></#list>) {
<#list entity.property as property>
        this.${property.name} = ${property.name};
</#list>
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
<#list entity.property as property>

    public <@map type=property.type/> get${property.name?cap_first}() {
        return ${property.name};
    }

    public void set${property.name?cap_first}(<@map type=property.type/> ${property.name}) {
        this.${property.name} = ${property.name};
    }
</#list>
}