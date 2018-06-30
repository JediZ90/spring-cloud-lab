package lab.cloud.bookstore.web.resource;

import org.springframework.hateoas.ResourceSupport;

import lab.cloud.bookstore.web.model.Book;

public class BookResource extends ResourceSupport {
    private final Book book;

    BookResource(Book book) {
        this.book = book;
    }

    public String getIsbn() {
        return book.getIsbn();
    }

    public String getTitle() {
        return book.getTitle();
    }

    public String getAuthor() {
        return book.getAuthor();
    }
}