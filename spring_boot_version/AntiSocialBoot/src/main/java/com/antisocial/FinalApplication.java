package com.antisocial;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import java.io.IOException;

@SpringBootApplication//(exclude={MultipartAutoConfiguration.class}) //(exclude = {ErrorMvcAutoConfiguration.class})
@EnableCaching
@EnableTransactionManagement
public class FinalApplication extends SpringBootServletInitializer {

	/**
	 * Extending SpringBootServletInitializer help us
	 * to deploy this apps war file to tomcat outside of production.
	 *
	 * @author Ant Kaynak github/Exercon
	* */

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(FinalApplication.class);
	}


	public static void main(String[] args) {
		SpringApplication.run(FinalApplication.class, args);
	}

}
