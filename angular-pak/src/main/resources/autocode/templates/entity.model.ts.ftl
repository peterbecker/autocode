interface ${entity.name} {
<#list entity.property as property>
    ${property.name}:${property.type};
</#list>
}