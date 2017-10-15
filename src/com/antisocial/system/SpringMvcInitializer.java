package com.antisocial.system;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import javax.servlet.ServletRegistration;


public class SpringMvcInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * This class is needed for no web.xml configuration.
     * Customize Registration registers unknown URL exceptions.
     * We then catch those exceptions in Exception Controller.
     *
     *  @author Ant Kaynak - Github/Exercon
     * */

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {SpringConfiguration.class} ;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

    @Override
    public void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setInitParameter("throwExceptionIfNoHandlerFound", "true");
    }






}
