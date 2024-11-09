package org.example;

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
        System.setProperty("java.security.policy", "C:\\Users\\44410\\IdeaProjects\\java-code-audit\\rmi\\server\\src\\main\\resources\\server.policy");
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            String name = "Compute";
            Compute engine = new ServerCompute();
            Compute stub =
                (Compute) UnicastRemoteObject.exportObject(engine, 0);
//            Registry reg = LocateRegistry.createRegistry(8971);
            Registry reg = LocateRegistry.getRegistry(8971);

            reg.rebind(name, stub);
            System.out.println("ServerCompute bound");
        } catch (Exception e) {
            System.err.println("ServerCompute exception:");
            e.printStackTrace();
        }
    }
}