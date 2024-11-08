package org.example.no1;

public class User implements AutoCloseable{
    private String test;

    public void setTest(String test) {
        System.out.println("call setTest");
        System.out.println("test value: " + test);
        this.test = test;
    }

    @Override
    public void close() throws Exception {

    }
}