package com.kl.ecommerce.utils;


import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

public class BeanFactory {

    public static Object createDaoInstance (String name) {

        try {
            SAXReader reader = new SAXReader();
            InputStream inputStream = BeanFactory.class.getClassLoader().getResourceAsStream("application.xml");
            Document document = reader.read(inputStream);
            Element rootElement = document.getRootElement();
            List<Element> elements = rootElement.elements();

            for (Element ele :
                    elements) {
                String id = ele.attributeValue("id");
                if (id.equals(name)) {
                    String classPath = ele.attributeValue("class");
                    Class clazz = Class.forName(classPath);
                    return clazz.getDeclaredConstructor().newInstance();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
