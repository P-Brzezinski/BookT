package pl.brzezinski.bookt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

@SpringBootApplication
public class BookTApplication {

    public static void main(String[] args) {

        SpringApplication.run(BookTApplication.class, args);
    }

    @Bean
    public Validator validator(){
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public SpringSecurityDialect securityDialect() {
        return new SpringSecurityDialect();
    }

}
