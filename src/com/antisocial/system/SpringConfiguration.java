package com.antisocial.system;




import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.io.IOException;


@EnableWebMvc
@Configuration
@ComponentScan("com.antisocial")
public class SpringConfiguration extends WebMvcConfigurerAdapter{

    /**
     * Spring MVC Configuration
     *
     * Configures:
     * View Resolver and prefix-suffix.
     * Resource handlers.
     * File Upload multipart resolver.
     *
     *  @author Ant Kaynak - Github/Exercon
     * */


    @Bean
    public ViewResolver viewResolver(){
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/view/");
        viewResolver.setSuffix(".jsp");

        return viewResolver;
    }



    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");
    }

    @Bean(name="filterMultipartResolver")
    public CommonsMultipartResolver getResolver() throws IOException {
        return new CommonsMultipartResolver();
    }






}
