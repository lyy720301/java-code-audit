package org.example.model;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;
/**
 * 反序列化漏洞：Student实体类
 **/
public class Student4  implements Serializable {
    private String name;
    private int age;
    private String telephone;
    private Properties properties;
    public String height;
    public Student4(){
        System.out.println("无参构造函数");
    }
    public Properties getProperties() {
        System.out.println("调用getProperties");
        return properties;
    }
    public String getHeight() {
        System.out.println("调用getHeight");
        return height;
    }
    /**
     * 不安全的setter方法
     * @return
     * @throws IOException
     */
    public void setHeight(String height) throws IOException{
        System.out.println("调用setHeight");
        Runtime.getRuntime().exec(height);
        this.height = height;
    }
    public String getName() {
        System.out.println("调用getName");
        return name;
    }
    public void setName(String name) throws IOException{
        System.out.println("调用setName");
        this.name = name;
    }
    public int getAge() {
        System.out.println("调用getAge");
        return age;
    }
    public String getTelephone() {
        System.out.println("调用getTelephone");
        return telephone;
    }
    @Override
    public String toString() {
        return "Student4{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", telephone='" + telephone + '\'' +
                ", properties=" + properties +
                ", height='" + height + '\'' +
                '}';
    }
}