package lab.cloud.bookstore.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import lab.cloud.bookstore.web.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}