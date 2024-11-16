package org.example;

import java.io.File;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/*
请配置 vm options
-Djava.security.policy=绝对路径/server.policy
或者直接设置系统属性 System.setProperty
 */
public class ServerCompute implements Compute {

    public ServerCompute() {
        super();
    }

    public <T> T executeTask(Task<T> t) {
        System.out.println("B: 执行executeTask");
        return t.execute();
    }

    public static void main(String[] args) {
        // 获取项目根目录
        String basePath = new File("").getAbsolutePath();
        // 构建相对路径
        String policyPath = basePath + "/rmi/server/src/main/resources/server.policy";
        System.setProperty("java.security.policy", policyPath);
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            String name = "Compute";
            Compute engine = new ServerCompute();
            Compute stub =
                (Compute) UnicastRemoteObject.exportObject(engine, 0);
            Registry reg = LocateRegistry.getRegistry(8971);
            reg.rebind(name, stub);
            // 再发布一个
            NewApi newApi = new NewApiImpl();
            NewApi stub2 =
                    (NewApi) UnicastRemoteObject.exportObject(newApi, 0);
            reg.rebind("NewApi", stub2);
            System.out.println("ServerCompute bound");
        } catch (Exception e) {
            System.err.println("ServerCompute exception:");
            e.printStackTrace();
        }
    }
}