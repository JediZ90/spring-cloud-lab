package lab.cloud.bookstore.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import lab.cloud.bookstore.web.model.BookStore;

public interface BookStoreRepository extends JpaRepository<BookStore, String> {}