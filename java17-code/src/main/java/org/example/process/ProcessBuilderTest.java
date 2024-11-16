package org.example.process;

public class ProcessBuilderTest {
    public static void main(String[] args) {
        try {
            new ProcessBuilder("calc").start();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
