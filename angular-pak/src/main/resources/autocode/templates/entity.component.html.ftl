<dl>
    <#list entity.property as property>
        <dt>${property.name}</dt>
        <dd>{{item.${property.name}}}</dd>
    </#list>
</dl>