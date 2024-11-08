package org.example;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.example.model.Student;
import org.example.model.Student3;
import org.junit.jupiter.api.Test;

public class FastjsonTest {

    // 序列化
    @Test
    public void test1() {
        Student student = new Student();
        student.setAge(5);
        student.setName("Tom");
        student.setTelephone("123456");
        String jsonstring = JSON.toJSONString(student);
        System.out.println(jsonstring);
    }

    // 序列化
    @Test
    public void test2() {
        Student student = new Student();
        student.setAge(5);
        student.setName("Tom");
        student.setTelephone("123456");
        String jsonstring = JSON.toJSONString(student, SerializerFeature.WriteClassName);
        /*
        {"@type":"org.example.model.Student","age":5,"name":"Tom","telephone":"123456"}
         */
        System.out.println(jsonstring);
    }

    // 反序列化
    @Test
    public void test3() {
        String jsonString = "{\"@type\":\"org.example.model.Student\",\"age\":5,\"name\":\"Tom\",\"telephone\":\"123456\"}";
        Student obj = JSON.parseObject(jsonString, Student.class, Feature.SupportNonPublicField);
        System.out.println(obj);
        System.out.println(obj.getClass());
    }

    @Test
    public void test4() {
        String jsonString = "{\"@type\":\"Student\",\"age\":5,\"name\":\"Tom\",\"telephone\":\"123456\"}";
        Object obj = JSON.parseObject(jsonString);
        System.out.println(obj);
        System.out.println(obj.getClass());
    }

    /**
     * 调用JSON.parseObject(String text，Class class) 反序列化时，若存在setProperties，则 不会再调用getProperties。 若我们添加其setter方法，再次测试：
     */
    @Test
    public void test5() {
        String jsonString = "{\"@type\":\"org.example.model.Student3\",\"age\":5,\"name\":\"Tom\",\"t elephone\":\"123456\",\"properties\":{}}";
        Object obj = JSON.parseObject(jsonString/*, Student3.class*/);
        System.out.println(obj);
        System.out.println(obj.getClass());
    }

    @Test
    public void test6() {
        String jsonString = "{\"@type\":\"org.example.model.Student4\",\"age\":5,\"name\":\"Tom\",\"telephone\":\"123456\",\"height\":\"calc\",\"properties\":{}}";
        Object obj = JSON.parseObject(jsonString);
        System.out.println(obj);
        System.out.println(obj.getClass());
    }
}
