package org.example;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.math.BigDecimal;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Hashtable;

/*
请配置 vm options
-Djava.security.policy=绝对路径/server.policy
 */
public class Client {

    static String RMI_NAME = "Compute";

    public static void main(String[] args) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {

            PiTask task = new PiTask(Integer.parseInt("2"));
            Compute comp = getByJNDI();
            BigDecimal pi = comp.executeTask(task);
            System.out.println(pi);
        } catch (Exception e) {
            System.err.println("ComputePi exception:");
            e.printStackTrace();
        }
    }

    // 直接获取接口
    static Compute directGet() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(8971);
        return (Compute) registry.lookup(RMI_NAME);
    }


    // 使用JNDI获取接口
    static Compute getByJNDI() {
        String providerURL = "rmi://localhost:8971";

        // 创建环境变量对象
        Hashtable env = new Hashtable();

        // 设置JNDI初始化工厂类名
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");

        // 设置JNDI提供服务的RMI地址
        env.put(Context.PROVIDER_URL, providerURL);
        try {
            DirContext context = new InitialDirContext(env);
            Compute compute = (Compute) context.lookup(RMI_NAME);
            return compute;
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }

    }

}