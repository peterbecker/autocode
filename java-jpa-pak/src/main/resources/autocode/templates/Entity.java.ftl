package ${package};

import java.util.*;
import javax.persistence.*;

@Entity
public class ${entity.name} {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

<#list entity.property as property>
    <#if property.linkType == "SET">
    @OneToMany(fetch = FetchType.EAGER)
    </#if>
    private <@map type=property.type linkType=property.linkType/> ${property.name};

</#list>
    public ${entity.name}() {
    }

    public ${entity.name}(<#list entity.property as property><@map type=property.type linkType=property.linkType/> ${property.name}<#if property_has_next>, </#if></#list>) {
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

    public <@map type=property.type linkType=property.linkType/> get${property.name?cap_first}() {
        return ${property.name};
    }

    public void set${property.name?cap_first}(<@map type=property.type linkType=property.linkType/> ${property.name}) {
        this.${property.name} = ${property.name};
    }
</#list>
}