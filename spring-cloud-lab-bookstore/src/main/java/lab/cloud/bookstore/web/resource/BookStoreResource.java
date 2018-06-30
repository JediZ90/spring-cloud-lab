package lab.cloud.bookstore.web.resource;

import org.springframework.hateoas.ResourceSupport;

import java.util.Collection;

public class BookStoreResource extends ResourceSupport {

    private final Collection<BookResource> bookResources;

    BookStoreResource(Collection<BookResource> bookResources) {
        this.bookResources = bookResources;
    }

    public Collection<BookResource> getBooks() {
        return bookResources;
    }
}