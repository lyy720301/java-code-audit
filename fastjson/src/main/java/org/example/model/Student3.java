package org.example.model;
import com.alibaba.fastjson.JSON;
import java.util.Properties;
public class Student3 {
    private String name;
    private int age;
    private String telephone;
    private Properties properties;
    public Student3() {
        System.out.println("无参构造函数");
    }
    public Properties getProperties() {
        System.out.println("调用getProperties");
        return properties;
    }
    public void setProperties(Properties properties) {
     System.out.println("调用setProperties");
     this.properties = properties;
    }
    public String getName() {
        System.out.println("调用getName");
        return name;
    }
    public int getAge() {
        System.out.println("调用getAge");
        return age;
    }
    public String getTelephone() {
        System.out.println("调用getTelephone");
        return telephone;
    }
    public void setName(String name) {
        System.out.println("调用setName");
        this.name = name;
    }
    public void setAge(int age) {
        System.out.println("调用setAge");
        this.age = age;
    }
    public void setTelephone(String telephone) {
        System.out.println("调用setTelephone");
        this.telephone = telephone;
    }
    @Override
    public String toString() {
        return "Student2{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", telephone='" + telephone + '\'' +
                ", properties=" + properties +
                '}';
    }

   //反序列化
    public static void fanxuliehua05(){
        String jsonString = "{\"@type\":\"Student3\",\"age\":5,\"name\":\"Tom\",\"t elephone\":\"123456\",\"properties\":{}}";
        Object obj = JSON.parseObject(jsonString, Object.class);
        System.out.println(obj);
        System.out.println(obj.getClass());
    }
}