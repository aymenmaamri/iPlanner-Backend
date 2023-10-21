package hackathon.backend.iplanner;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/planning-room")
                .allowedOrigins("http://localhost")
                .allowedMethods("POST", "GET")
                .allowedHeaders("*");

        registry.addMapping("/planning-room/join")
                .allowedOrigins("http://localhost")
                .allowedMethods("POST", "GET")
                .allowedHeaders("*");

        registry.addMapping("/createUser")
                .allowedOrigins("http://localhost:3001")
                .allowedMethods("*")
                .allowedHeaders("*");
    }
}
