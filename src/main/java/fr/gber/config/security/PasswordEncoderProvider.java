package main.java.fr.gber.config.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderProvider {

    public static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

}
