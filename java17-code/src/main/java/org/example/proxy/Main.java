package org.example.proxy;

import java.lang.reflect.Proxy;

/**
 * 动态代理：一套代理逻辑可以代理多个接口
 * 运行时生成代理类
 *
 */
public class Main {
    public static void main(String[] args) {
        // 原始对象
        HelloService helloService = new HelloServiceImpl();
        ByeService byeService = new ByeServiceImpl();

        // 创建代理对象
        HelloService proxyInstance = (HelloService) Proxy.newProxyInstance(
            helloService.getClass().getClassLoader(),
            helloService.getClass().getInterfaces(),
            new LogInvocationHandler(helloService)
        );

        // 创建代理对象
        /**
         * 这个代理类继承了Proxy，实现了ByeService
         */
        ByeService byeServiceProxy = (ByeService) Proxy.newProxyInstance(
                byeService.getClass().getClassLoader(),
                byeService.getClass().getInterfaces(),
                new LogInvocationHandler(byeService)
        );

        // 调用代理方法
        proxyInstance.sayHello("Alice");
        byeServiceProxy.sayBye("lz");
    }
}
