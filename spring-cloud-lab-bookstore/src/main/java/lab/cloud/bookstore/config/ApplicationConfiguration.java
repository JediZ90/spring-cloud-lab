package lab.cloud.bookstore.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnCloudPlatform;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.cloud.CloudPlatform;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.util.UriComponentsBuilder;

import lab.cloud.bookstore.web.model.ApplicationInformation;

@Configuration
public class ApplicationConfiguration {

    @Bean
    @ConditionalOnCloudPlatform(CloudPlatform.CLOUD_FOUNDRY)
    public ApplicationInformation cloudFoundryApplicationInformation(Environment environment) {
        String uri = environment.getProperty("vcap.application.uris[0]");

        String baseUrl = UriComponentsBuilder.newInstance().scheme("https").host(uri).build().toUriString();

        return new ApplicationInformation(baseUrl);
    }

    @Bean
    @ConditionalOnProperty("KUBERNETES_SERVICE_HOST")
    public ApplicationInformation kubernetesApplicationInformation(Environment environment) {
        String uri = environment.getProperty("KUBERNETES_SERVICE_HOST");
        String port = environment.getProperty("KUBERNETES_SERVICE_PORT");

        String baseUrl = UriComponentsBuilder.newInstance().scheme("https").host(uri).port(port).build().toUriString();

        return new ApplicationInformation(baseUrl);
    }

    @Bean
    @ConditionalOnMissingBean(ApplicationInformation.class)
    public ApplicationInformation defaultApplicationInformation() {
        String baseUrl = UriComponentsBuilder.newInstance().scheme("http").host("localhost").port(8080).build()
                        .toUriString();

        return new ApplicationInformation(baseUrl);
    }
}