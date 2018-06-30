package lab.cloud.bookstore.web.resource;

import java.util.List;

import lab.cloud.bookstore.web.controller.BookStoreController;
import lab.cloud.bookstore.web.model.BookStore;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class BookStoreResourceAssembler {

    public BookStoreResource toResource(BookStore bookStore) {
        BookResourceAssembler bookAssembler = new BookResourceAssembler();
        List<BookResource> bookResources = bookAssembler.toResources(bookStore.getBooks(), bookStore.getId());

        BookStoreResource bookStoreResource = new BookStoreResource(bookResources);
        bookStoreResource.add(linkTo(BookStoreController.class).slash(bookStore.getId()).withSelfRel());
        return bookStoreResource;
    }
}