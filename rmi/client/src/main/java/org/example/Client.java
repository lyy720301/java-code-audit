package org.example;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.math.BigDecimal;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Hashtable;

/*
请配置 vm options
-Djava.security.policy=绝对路径/server.policy
 */
public class Client {

    static String RMI_COMPUTE = "Compute";
    static String RMI_NEW_API = "NewApi";

    public static void main(String[] args) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {

            PiTask task = new PiTask(Integer.parseInt("2"));
            Compute comp = (Compute) directGet(RMI_COMPUTE);
            BigDecimal pi = comp.executeTask(task);
            System.out.println(pi);
            NewApi newApi = (NewApi) directGet(RMI_NEW_API);
            newApi.run();

            jndi2();
        } catch (Exception e) {
            System.err.println("ComputePi exception:");
            e.printStackTrace();
        }
    }

    // 直接获取接口
    static Remote directGet(String rmiName) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(8971);
        return registry.lookup(rmiName);
    }

    static void jndi2() {
        try {
            InitialContext initialContext = new InitialContext();
            Object lookup = initialContext.lookup("rmi://localhost:8971/" + RMI_NEW_API);
            ((NewApi) lookup).run();
        } catch (NamingException e) {
            throw new RuntimeException(e);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

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
            Compute compute = (Compute) context.lookup(RMI_COMPUTE);
            return compute;
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }

    }

}