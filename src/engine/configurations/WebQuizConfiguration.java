package engine.configurations;

import engine.entities.Quiz;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;

@Configuration
public class WebQuizConfiguration {
    @Bean
    ArrayList<Quiz> getList() {
        return new ArrayList<Quiz>();
    }

}
