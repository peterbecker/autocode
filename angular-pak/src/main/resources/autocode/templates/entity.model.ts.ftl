export interface ${entity.name} {
    id: Number;
<#list entity.property as property>
    ${property.name}:<@map type=property.type/>;
</#list>
}
