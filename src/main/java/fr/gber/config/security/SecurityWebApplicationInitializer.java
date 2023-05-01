package main.java.fr.gber.config.security;

import main.java.fr.gber.config.webmvc.WebConfig;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;


public class SecurityWebApplicationInitializer extends
        AbstractSecurityWebApplicationInitializer {

    public SecurityWebApplicationInitializer() {
        super(WebConfig.class);
        System.out.println("SecurityWebApplicationInitializer - SecurityWebApplicationInitializer");
    }
}