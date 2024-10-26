package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        String command = "ping google.com";
        Process proc = Runtime.getRuntime().exec(command); //打印执行结果
        InputStream in = proc.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "GBK"));
        String line = null;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
    }
}
