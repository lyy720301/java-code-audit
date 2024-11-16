package org.example.serializable;

import java.io.*;
import java.util.HashMap;

public class SerializableTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File("./Animal.txt")));
        Animal animal = new Animal();
        animal.name = "dog";
        animal.map = new HashMap();
        animal.map.put("1", "2");
//        animal.pig = new Pig("哈哈pig");
        objectOutputStream.writeObject(animal);
        objectOutputStream.flush();
        objectOutputStream.close();

        // 反序列化
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File("./Animal.txt")));
        Animal a = (Animal) objectInputStream.readObject();
        objectInputStream.close();
        System.out.println(a.name);
        System.out.println(a.map.size());
        System.out.println(animal.pig);
    }
}
