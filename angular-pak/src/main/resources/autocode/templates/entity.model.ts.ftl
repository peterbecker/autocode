interface ${entity.name}Model {
<#list entity.property as property>
    ${property.name}:${property.type};
</#list>
}