package org.example.serializable;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

public class Animal implements Serializable {
    public Animal() {
        System.out.println("反序列化时不会被调用");
    }

    public String name;

    // 这个属性不能被序列化
    public Pig pig;

    public HashMap map;

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException{
        in.defaultReadObject();
        System.out.println("反序列化");
        Runtime.getRuntime().exec("calc");
    }

}
