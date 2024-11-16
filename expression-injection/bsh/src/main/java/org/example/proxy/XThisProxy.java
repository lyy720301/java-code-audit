package org.example.proxy;

import bsh.EvalError;
import bsh.Interpreter;
import bsh.XThis;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * XThis 代理测试
 */
public class XThisProxy {
    public static void main(String[] args) throws EvalError, NoSuchFieldException, IllegalAccessException, IOException {

        // 将 compare 方法注册至 Interpreter 实例上下文中
        Interpreter i = new Interpreter();
        i.source(new File("").getAbsoluteFile() + "/expression-injection/bsh/src/main/resources/xthisproxy.bsh");

        // 创建 XThis 对象，获取其 invocationHandler
        XThis xt = new XThis(i.getNameSpace(), i);
        Field handlerField = XThis.class.getDeclaredField("invocationHandler");
        handlerField.setAccessible(true);
        InvocationHandler handler = (InvocationHandler) handlerField.get(xt);
        // 使用 XThis$Handler 为 Animal 创建动态代理
        Animal animal = (Animal) Proxy.newProxyInstance(
                Animal.class.getClassLoader(), new Class<?>[]{Animal.class}, handler);
        // print '.bsh eating...'
        animal.eat();

//        animal.up(); // throw error
    }
}
