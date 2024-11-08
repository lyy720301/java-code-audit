package org.example.no1;

import java.io.IOException;

public class EvilCalc {
    public EvilCalc() throws IOException {
        Runtime.getRuntime().exec("calc");
    }

    public static void main(String[] args) throws IOException {
        new EvilCalc();
    }
}
