package org.example.process;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射静态方法测试
 */
public class ReflectBeanTest {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<ReflectBean> refectBeanClass = ReflectBean.class;
        Method run = refectBeanClass.getMethod("run");
        Object invoke = run.invoke(null);
        System.out.println(invoke);

    }
}
