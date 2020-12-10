package luabui.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class Application {

//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedMethods("GET", "POST", "PUT", "DELETE")
//                        .allowedHeaders("Access-Control-Allow-Origin", "*")
//                        .allowedOrigins("http://foodgood.surge.sh/", "http://foodgood.surge.sh", "http://foodgood.surge.sh/**", "http://localhost:8085/");
//            }
//        };
//    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
