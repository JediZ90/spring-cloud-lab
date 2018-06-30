package lab.cloud.bookstore.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import lab.cloud.bookstore.web.repository.BookStoreRepository;

@Configuration
@EnableJpaRepositories(basePackageClasses = BookStoreRepository.class)
public class BookstoreRepositoryConfiguration {}