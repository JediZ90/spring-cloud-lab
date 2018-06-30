package lab.cloud.bookstore.web.security;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import lab.cloud.bookstore.web.service.UserService;

@Component
public class UserApplicationListener implements ApplicationListener<ApplicationReadyEvent> {

    private UserService userService;

    public UserApplicationListener(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        userService.initializeUsers();
    }
}