package lab.cloud.bookstore.web.resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import lab.cloud.bookstore.web.controller.BookController;
import lab.cloud.bookstore.web.model.Book;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class BookResourceAssembler {

    public BookResource toResource(Book book, String bookStoreId) {
        BookResource bookResource = new BookResource(book);
        bookResource.add(linkTo(BookController.class, bookStoreId).slash(book.getId()).withSelfRel());

        return bookResource;
    }

    public List<BookResource> toResources(Collection<Book> books, String bookStoreId) {
        return books.stream().map(book -> toResource(book, bookStoreId))
                        .collect(Collectors.toCollection(() -> new ArrayList<>(books.size())));
    }
}