package org.example.serialize.proxy;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class HandlerImpl implements InvocationHandler, Serializable {

    public Map map;

    public HandlerImpl(Map map) {
        this.map = map;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            System.out.println("proxyhashcode: "  + "  map hashcode: " );
            System.out.println(method.getName());
            return "hh";
        }  catch (Exception e) {
            throw new RuntimeException("Unexpected invocation exception: " + e.getMessage());
        }
    }

    private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
//        var1.defaultReadObject();
        ObjectInputStream.GetField getField = var1.readFields();
        this.map = (Map)getField.get("map", null);
        System.out.println("hello");
    }
}
