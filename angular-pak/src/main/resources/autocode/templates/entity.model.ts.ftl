export interface ${entity.name} {
    id: Number;
<#list entity.property as property>
    ${property.name}:${property.type};
</#list>
}