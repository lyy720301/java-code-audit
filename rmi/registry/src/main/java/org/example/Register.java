package org.example;

import java.io.File;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Register {
    public static void main(String[] args) {
        try {
            // 获取项目根目录
            String basePath = new File("").getAbsolutePath();
            // 构建相对路径
            String policyPath = basePath + "/rmi/registry/src/main/resources/server.policy";
            System.setProperty("java.security.policy", policyPath);
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new SecurityManager());
            }
            LocateRegistry.createRegistry(8971);
            System.out.println("RMI registry started on port 8971");
            // 避免程序退出
            Thread.currentThread().join();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}