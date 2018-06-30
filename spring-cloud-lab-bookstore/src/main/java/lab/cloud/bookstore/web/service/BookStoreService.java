package lab.cloud.bookstore.web.service;

import org.springframework.stereotype.Service;

import lab.cloud.bookstore.web.model.Book;
import lab.cloud.bookstore.web.model.BookStore;
import lab.cloud.bookstore.web.repository.BookStoreRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class BookStoreService {

    private BookStoreRepository repository;

    public BookStoreService(BookStoreRepository bookStoreRepository) {
        this.repository = bookStoreRepository;
    }

    public BookStore createBookStore(String storeId) {
        BookStore bookStore = new BookStore(storeId);

        return repository.save(bookStore);
    }

    public BookStore createBookStore() {
        return createBookStore(generateRandomId());
    }

    public BookStore getBookStore(String storeId) {
        Optional<BookStore> store = repository.findById(storeId);
        return store.orElseThrow(() -> new IllegalArgumentException("Invalid book store ID " + storeId + "."));
    }

    public void deleteBookStore(String id) {
        repository.deleteById(id);
    }

    public Book putBookInStore(String storeId, Book book) {
        String bookId = generateRandomId();
        Book bookWithId = new Book(bookId, book);

        BookStore store = getBookStore(storeId);
        store.addBook(bookWithId);

        repository.save(store);

        return bookWithId;
    }

    public Book getBookFromStore(String storeId, String bookId) {
        BookStore store = getBookStore(storeId);
        return store.getBookById(bookId).orElseThrow(
                        () -> new IllegalArgumentException("Invalid book ID " + storeId + ":" + bookId + "."));
    }

    public Book removeBookFromStore(String storeId, String bookId) {
        BookStore store = getBookStore(storeId);
        return store.remove(bookId).orElseThrow(
                        () -> new IllegalArgumentException("Invalid book ID " + storeId + ":" + bookId + "."));
    }

    private String generateRandomId() {
        return UUID.randomUUID().toString();
    }
}