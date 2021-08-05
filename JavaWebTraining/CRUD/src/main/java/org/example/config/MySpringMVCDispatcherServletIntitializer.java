package org.example.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MySpringMVCDispatcherServletIntitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {SpringConfig.class}; // задание spring конфигурации
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"}; // выполнять отправление на DispatcherServlet для всех URL
    }
}
