package lab.cloud.echo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class EchoController {

    @Value("${message:Hello default}")
    private String message;

    @RequestMapping("/echo")
    public String echo() {
        return this.message;
    }
}