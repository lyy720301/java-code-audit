package org.example.serialize.proxy;

import org.apache.commons.collections.map.LazyMap;
import org.example.serialize.Gadgets;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class SerProxyTest {

    public static void main(String[] args) throws Exception {


        final Map innerMap = new HashMap();

        final Map mapProxy = Gadgets.createMemoitizedProxy(innerMap, Map.class);
        final InvocationHandler handler = Gadgets.createMemoizedInvocationHandler(mapProxy);


        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("ss.txt"));
        objectOutputStream.writeObject(handler);
        objectOutputStream.flush();
        objectOutputStream.close();
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("ss.txt"));

        HandlerImpl ih =(HandlerImpl) objectInputStream.readObject();

        objectInputStream.close();
//        ih.map.put("hello", "world");
        System.out.println();

    }
}
