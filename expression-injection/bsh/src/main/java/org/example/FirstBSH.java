package org.example;

import bsh.EvalError;
import bsh.Interpreter;
import bsh.XThis;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * bsh初探
 */
public class FirstBSH {
    public static void main(String[] args) throws EvalError, IOException {

        Interpreter i = new Interpreter();  // Construct an interpreter
        i.set("foo", 5);                    // Set variables
        i.set("date", new Date());

        Date date = (Date) i.get("date");    // retrieve a variable
        System.out.println("date " + date);
        // Eval a statement and get the result
        i.eval("bar = foo*10");
        System.out.println("bar " + i.get("bar"));
        // Source an external script file
        i.source(new File("").getAbsoluteFile() + "/expression-injection/bsh/src/main/resources/hello.bsh");
        // 提供给java程序的接口，用于执行bsh
        XThis xThis = new XThis(i.getNameSpace(), i);
//        i.eval("exec(\"calc\")");
        System.out.println("---");
        xThis.invokeMethod("helloWorld", new Object[]{});
    }
}