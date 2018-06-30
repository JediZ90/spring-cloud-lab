package lab.cloud.bookstore.servicebroker.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import lab.cloud.bookstore.servicebroker.repository.ServiceInstanceRepository;

@Configuration
@EnableJpaRepositories(basePackageClasses = ServiceInstanceRepository.class)
public class ServicesRepositoryConfiguration {}