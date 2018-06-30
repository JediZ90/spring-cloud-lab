package lab.cloud.bookstore.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import lab.cloud.bookstore.web.security.BookStorePermissionEvaluator;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class MethodSecurityConfiguration {
    @Bean
    BookStorePermissionEvaluator permissionEvaluator() {
        return new BookStorePermissionEvaluator();
    }
}