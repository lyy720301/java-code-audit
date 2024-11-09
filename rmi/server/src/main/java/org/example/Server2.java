package org.example;

import com.sun.jndi.rmi.registry.ReferenceWrapper;

import javax.naming.NamingException;
import javax.naming.Reference;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server2 {
    public static void main(String[] args) throws RemoteException, NamingException, AlreadyBoundException {
        // 提前起一个服务，根目录下放上test.class文件
        String url = "http://127.0.0.1:888/";
        Reference reference = new Reference("test", "test", url);
        ReferenceWrapper referenceWrapper = new ReferenceWrapper(reference);
        Registry reg = LocateRegistry.getRegistry(8971);
        reg.rebind("obj",referenceWrapper);
        System.out.println("running");
    }
}
