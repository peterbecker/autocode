<@imports entity=entity>
import {${type}} from "${type?lower_case}/${type?lower_case}.model";
</@imports>
export interface ${entity.name} {
    id: Number;
<#list entity.property as property>
    ${property.name}:<@map type=property.type/>;
</#list>
}
