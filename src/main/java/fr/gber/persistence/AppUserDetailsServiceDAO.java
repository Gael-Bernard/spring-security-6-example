package main.java.fr.gber.persistence;

import java.util.Collection;
import java.util.List;

import main.java.fr.gber.config.security.PasswordEncoderProvider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AppUserDetailsServiceDAO implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(final String username)
            throws UsernameNotFoundException {

        System.out.println("AppUserDetailsServiceDAO - loadUserByUsername");
        System.out.println("Purpose: Spring Security tries retrieving information about a user by calling custom DAO AppUserDetailsServiceDAO::loadUserByUsername, with username = "+username);

        if(!username.equals("pankaj")){
            System.out.println("Username was not found by AppUserDetailsServiceDAO.");
            throw new UsernameNotFoundException(username + " not found");
        }

        //creating dummy user details, should do JDBC operations
        return new UserDetails() {

            private static final long serialVersionUID = 2059202961588104658L;

            @Override
            public boolean isEnabled() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public String getUsername() {
                System.out.println("UserDetails - getUsername");
                System.out.println("Purpose: In the user details returned by AppUserDetailsServiceDAO, Spring Security asks for the username.");
                return username;
            }

            @Override
            public String getPassword() {
                System.out.println("UserDetails - getPassword");
                System.out.println("Purpose: In the user details returned by AppUserDetailsServiceDAO, Spring Security asks for the password.");
                // Note: Spring Security uses the encoded version of the password to know if it's the expected password.
                // Therefore, this method must also return the encoded version of the password.
                final String password = "pankaj123";
                final BCryptPasswordEncoder encoder = PasswordEncoderProvider.ENCODER;
                final String encodedPassword = encoder.encode("pankaj123");
                return encodedPassword;
            }

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                System.out.println("UserDetails - getAuthorities");
                System.out.println("Purpose: In the user details returned by AppUserDetailsServiceDAO, Spring Security asks for the roles granting permissions to this user.");
                List<SimpleGrantedAuthority> auths = new java.util.ArrayList<>();
                auths.add(new SimpleGrantedAuthority("admin"));
                return auths;
            }
        };
    }

}