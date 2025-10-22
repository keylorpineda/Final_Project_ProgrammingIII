package finalprojectprogramming.project.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import finalprojectprogramming.project.security.hash.PepperedPasswordEncoder;

@Configuration
public class PasswordEncoderConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder(@Value("${security.password.pepper:}") String pepper) {
        PasswordEncoder delegate = new BCryptPasswordEncoder(10);
        if (pepper == null || pepper.isBlank()) {
            return delegate;
        }
        return new PepperedPasswordEncoder(delegate, pepper);
    }
}