package org.example.process;

import java.lang.reflect.InvocationTargetException;

public class ReflectTest {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class clazz = Class.forName("java.lang.Runtime");
        clazz.getMethod("exec", String.class)
                .invoke(clazz.getMethod("getRuntime").invoke(clazz), "calc.exe");
    }
}
