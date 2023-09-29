package ru.itis.freemarker_example;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException, TemplateException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);
        cfg.setDirectoryForTemplateLoading(new File(System.getProperty("user.dir")));
        cfg.setDefaultEncoding("UTF-8");

        Map<String, Object> root = new HashMap<>();
        root.put("Name", "Halitov Ildus");
        root.put("N", 7);

        Template temp = cfg.getTemplate("page.ftl");

        Writer out = new OutputStreamWriter(System.out);
        temp.process(root, out);
    }
}
