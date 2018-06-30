package lab.cloud.bookstore.web.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lab.cloud.bookstore.web.model.User;
import lab.cloud.bookstore.web.repository.UserRepository;
import lab.cloud.bookstore.web.security.SecurityAuthorities;

import java.security.SecureRandom;

@Service
public class UserService {

    private static final String PASSWORD_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int PASSWORD_LENGTH = 12;

    private static final SecureRandom RANDOM = new SecureRandom();

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void initializeUsers() {
        if (userRepository.count() == 0) {
            userRepository.save(adminUser());
        }
    }

    public User createUser(String username, String... authorities) {
        String password = generatePassword();
        String encodedPassword = passwordEncoder.encode(password);

        userRepository.save(new User(username, encodedPassword, authorities));

        return new User(username, password, authorities);
    }

    public void deleteUser(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            userRepository.deleteById(user.getId());
        }
    }

    private User adminUser() {
        return new User("admin", passwordEncoder.encode("supersecret"), SecurityAuthorities.ADMIN,
                        SecurityAuthorities.FULL_ACCESS);
    }

    private String generatePassword() {
        StringBuilder sb = new StringBuilder(PASSWORD_LENGTH);
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            sb.append(PASSWORD_CHARS.charAt(RANDOM.nextInt(PASSWORD_CHARS.length())));
        }
        return sb.toString();
    }
}