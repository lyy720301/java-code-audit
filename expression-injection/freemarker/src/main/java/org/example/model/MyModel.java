package org.example.model;

import freemarker.template.TemplateModel;

public class MyModel implements TemplateModel {
    public MyModel() {
        System.out.println("myModel init...");
    }

    public String print() {
        return "model";
    }
}
