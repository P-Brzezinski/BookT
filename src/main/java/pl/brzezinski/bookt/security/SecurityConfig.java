package pl.brzezinski.bookt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/register").permitAll()
                    .antMatchers("/admin/*").hasAnyRole("ADMIN")
                    .antMatchers("/h2/**").permitAll()
                    .antMatchers("/restaurateurPanel/*").hasAnyRole("RESTAURATEUR", "ADMIN")
                    .anyRequest().authenticated()
                .and().formLogin().successHandler(myAuthenticationSuccessHandler())
                .and().csrf().ignoringAntMatchers("/h2/**")
                .and().headers().frameOptions().disable()
                .and().logout().permitAll().deleteCookies("JSESSIONID")
                .and().exceptionHandling().accessDeniedHandler(accessDeniedHandler);
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
        return new MyAccessSuccessHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return passwordEncoder;
    }

}