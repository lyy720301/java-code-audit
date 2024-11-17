package org.example;

import freemarker.template.TemplateModelException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FreemarkerConfiguration {
    @Autowired
    private freemarker.template.Configuration configuration;

    @PostConstruct
    public void configuration() throws TemplateModelException {
        this.configuration.setSharedVariable("app", "Spring Boot");
    }
}