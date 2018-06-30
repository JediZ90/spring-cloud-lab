package lab.cloud.echo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import lab.cloud.echo.controller.EchoController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EchoServerApplicationTest {

    @Autowired
    private ConfigurableEnvironment environment;

    @Autowired
    private EchoController controller;

    @Autowired
    private ContextRefresher refresher;

    @Test
    public void contextLoads() {
        assertThat(controller.echo()).isNotEqualTo("Hello test");
        TestPropertyValues.of("message:Hello test").applyTo(environment);
        assertThat(controller.echo()).isNotEqualTo("Hello test");
        refresher.refresh();
        assertThat(controller.echo()).isEqualTo("Hello test");
    }
}
