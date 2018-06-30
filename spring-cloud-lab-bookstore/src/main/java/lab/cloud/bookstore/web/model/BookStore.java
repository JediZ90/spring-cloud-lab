package lab.cloud.bookstore.web.model;

import org.springframework.hateoas.Identifiable;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "bookstores")
public class BookStore implements Identifiable<String> {

    @Id
    @Column(length = 50)
    private final String id;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "books", joinColumns = @JoinColumn(name = "bookstore_id"))
    private final List<Book> books = new ArrayList<>();

    @SuppressWarnings("unused")
    private BookStore() {
        this.id = null;
    }

    public BookStore(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    public List<Book> getBooks() {
        return this.books;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public Optional<Book> getBookById(String bookId) {
        return books.stream().filter(book -> book.getId().equals(bookId)).findFirst();
    }

    public Optional<Book> remove(String bookId) {
        Optional<Book> book = getBookById(bookId);

        book.ifPresent(books::remove);

        return book;
    }
}