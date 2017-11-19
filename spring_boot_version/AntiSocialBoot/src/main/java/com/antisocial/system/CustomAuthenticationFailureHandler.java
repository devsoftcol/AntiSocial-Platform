package com.antisocial.system;


import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    /**
     * Configures custom messages upon Spring Security authentication errors.
     *
     *  @author Ant Kaynak - Github/Exercon
     * */

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        setDefaultFailureUrl("/login?error");
        super.onAuthenticationFailure(request, response, exception);
        String errorMessage = "Invalid username and/or password!";

        if (exception.getMessage().equalsIgnoreCase("User is disabled")) {
            errorMessage = "User account is disabled! Check user e-mail to activate the account.";
        } else if (exception.getMessage().equalsIgnoreCase("User account has expired")) {
            errorMessage = "User account has expired. Please contact our support team.";
        }else if (exception.getMessage().equalsIgnoreCase("User account is locked")){
            errorMessage = "User account is banned. Please contact our support team.";
        }
        request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, errorMessage);
    }
}