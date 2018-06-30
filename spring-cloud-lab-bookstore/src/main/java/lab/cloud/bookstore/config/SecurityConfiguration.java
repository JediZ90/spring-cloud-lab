package lab.cloud.bookstore.config;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lab.cloud.bookstore.web.security.SecurityAuthorities;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http.csrf().disable().authorizeRequests().antMatchers("/bookstores/**").authenticated().antMatchers("/v2/**")
                        .hasAuthority(SecurityAuthorities.ADMIN).requestMatchers(EndpointRequest.to("info", "health"))
                        .permitAll().requestMatchers(EndpointRequest.toAnyEndpoint())
                        .hasAuthority(SecurityAuthorities.ADMIN).and().httpBasic();
        // @formatter:on
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}