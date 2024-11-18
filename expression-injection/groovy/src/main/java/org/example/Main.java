package org.example;

import groovy.lang.*;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
import org.codehaus.groovy.runtime.MethodClosure;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.*;
import java.nio.charset.Charset;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException, ScriptException, ResourceException, javax.script.ScriptException, InstantiationException, IllegalAccessException {
        GroovyShell groovyShell = new GroovyShell();
        groovyShell.evaluate("\"calc\".execute()");

        System.out.println("---");
        Script script = groovyShell.parse(new File("expression-injection/groovy/src/main/groovy/test.groovy"));
        script.run();

        // 允许从指定root（可以是某文件夹，某URL，某Resource）下获取脚本来执行，还可以指定类加载器去加载。
        System.out.println("---");
        GroovyScriptEngine groovyScriptEngine = new GroovyScriptEngine("");
        groovyScriptEngine.run("expression-injection/groovy/src/main/groovy/test.groovy",new Binding());

        // javax.script.ScriptEngine
        System.out.println("script engine---");
        ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("groovy");
        System.out.println(scriptEngine.eval("\"whoami\".execute().text"));

        // GroovyClassLoader
        GroovyClassLoader classLoader = new GroovyClassLoader();
        Class clazz2 = classLoader.parseClass(new File("expression-injection/groovy/src/main/groovy/class_test.groovy"));
        GroovyObject go = (GroovyObject)clazz2.newInstance();
        go.invokeMethod("main", new Object[]{});

        System.out.println("\n闭包 ---");
        MethodClosure clo = new MethodClosure("whoami", "execute");
        InputStream inputStream = ((Process) clo.call()).getInputStream();
        try {
            String s = inputStreamToString(inputStream);
            System.out.println(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String inputStreamToString(InputStream inputStream) throws Exception {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.defaultCharset()))) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }
}
