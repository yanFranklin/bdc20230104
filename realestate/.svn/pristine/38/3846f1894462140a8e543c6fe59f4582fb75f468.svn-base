<#macro html title="" import="" css="" js="">
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=EDGE">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
    <title>${title}</title>
    <#if import??>
        <#list import?split(",") as lib>
            <#switch lib>
                <#case "layui">
                    <@style name="lib/layui/css/layui.css" media="all"></@style>
                    <@script name="lib/layui/layui.js"></@script>
                    <@script name="lib/js/jquery.min.js"></@script>
                    <@style name="css/common.css?v=1.0"></@style>
                    <@script name="js/common.js"></@script>
                    <#break />
                <#case "layuiForm">
                    <@style name="css/form.css"></@style>
                    <@style name="css/index.css"></@style>
                    <@script name="js/form.js"></@script>
                    <#break />
                <#default>
            </#switch>
        </#list>
    </#if>
${css!}
</head>
<body>
    <#nested />
${js!}
</body>
</html>
</#macro>

<#macro rootPath>${springMacroRequestContext.getContextPath()}</#macro>

<#macro script name>
<script src="<@rootPath/>/${name}" type="text/javascript"></script>
</#macro>

<#macro style name media="screen">
<link href="<@rootPath/>/${name}" type="text/css" media="${media!}" rel="stylesheet"/>
</#macro>
