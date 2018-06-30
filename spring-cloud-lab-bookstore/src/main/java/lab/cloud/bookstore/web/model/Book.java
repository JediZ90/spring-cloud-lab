package lab.cloud.bookstore.web.model;

import org.springframework.hateoas.Identifiable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Book implements Identifiable<String> {

    @Column(length = 50)
    private final String id;

    @Column(length = 20)
    private final String isbn;

    @Column(length = 100)
    private final String title;

    @Column(length = 100)
    private final String author;

    @SuppressWarnings("unused")
    private Book() {
        this.id = null;
        this.isbn = null;
        this.title = null;
        this.author = null;
    }

    public Book(String isbn, String title, String author) {
        this.id = null;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
    }

    public Book(String id, Book book) {
        this.id = id;
        this.isbn = book.getIsbn();
        this.title = book.getTitle();
        this.author = book.getAuthor();
    }

    @Override
    public String getId() {
        return this.id;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public String getTitle() {
        return this.title;
    }

    public String getAuthor() {
        return this.author;
    }
}