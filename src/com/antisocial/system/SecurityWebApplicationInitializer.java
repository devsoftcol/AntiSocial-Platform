package com.antisocial.system;


import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.multipart.support.MultipartFilter;

import javax.servlet.ServletContext;

public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer{

    /**
     * Security Web Application Initializer
     *
     * Filtering for multi part file upload.
     * Adding listener to collect session events.
     *
     *  @author Ant Kaynak - Github/Exercon
     * */


    @Override
    protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
        insertFilters(servletContext, new MultipartFilter());
        servletContext.addListener(HttpSessionEventPublisher.class);
    }


}
