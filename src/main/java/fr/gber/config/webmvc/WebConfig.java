package main.java.fr.gber.config.webmvc;

import main.java.fr.gber.config.security.PasswordEncoderProvider;
import main.java.fr.gber.persistence.AppUserDetailsServiceDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@EnableWebMvc
@EnableWebSecurity
@Configuration
@ComponentScan(basePackages = {"main.java.fr.gber.controller"})
public class WebConfig implements WebMvcConfigurer {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println("WebConfig - addResourceHandlers");
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
    }

    @Bean
    public ViewResolver viewResolver() {
        System.out.println("WebConfig - viewResolver()");
        InternalResourceViewResolver bean = new InternalResourceViewResolver();

        bean.setViewClass(JstlView.class);
        bean.setPrefix("/WEB-INF/");
        bean.setSuffix(".jsp");

        return bean;
    }


    @Bean
    public UserDetailsService userDetailsService(BCryptPasswordEncoder bCryptPasswordEncoder) {
        System.out.println("WebConfig - userDetailsService");
        return new AppUserDetailsServiceDAO();
    }


    // @Override
    /* Deprecated in Spring Security 6 */
    public void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        /*
        System.out.println("WebConfig - configure(AuthenticationManagerBuilder auth)");

        // in-memory authentication
        // auth.inMemoryAuthentication().withUser("pankaj").password("pankaj123").roles("USER");

        // using custom UserDetailsService DAO
        auth.userDetailsService(new AppUserDetailsServiceDAO());

        // using JDBC
        /*
        Context ctx = new InitialContext();
        DataSource ds = (DataSource) ctx
                .lookup("java:/comp/env/jdbc/MyLocalDB");

        final String findUserQuery = "select username,password,enabled "
                + "from Employees " + "where username = ?";
        final String findRoles = "select username,role " + "from Roles "
                + "where username = ?";

        auth.jdbcAuthentication().dataSource(ds)
                .usersByUsernameQuery(findUserQuery)
                .authoritiesByUsernameQuery(findRoles);
         */
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        System.out.println("WebConfig - configure(WebSecurity web)");
        return (web) ->
        web
                .ignoring()
                // Spring Security should completely ignore URLs ending with .html
                .requestMatchers("/static/**");
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        System.out.println("WebConfig - passwordEncoder(WebSecurity web)");
        return PasswordEncoderProvider.ENCODER;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        System.out.println("WebConfig - filterChain");
        http
                .csrf()
                .disable()

                .authorizeHttpRequests()

                    .requestMatchers(HttpMethod.DELETE)
                    .hasRole("ADMIN")

                    .requestMatchers("/admin/**")
                    .hasAnyRole("ADMIN")

                    .requestMatchers("/user/**")
                    .hasAnyRole("USER", "ADMIN")

                    .requestMatchers("/login/**")
                    .anonymous()

                    .anyRequest()
                    .authenticated()

                .and()
                .httpBasic();

        return http.build();
    }

}
