package lab.cloud.bookstore.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lab.cloud.bookstore.web.model.BookStore;
import lab.cloud.bookstore.web.resource.BookStoreResource;
import lab.cloud.bookstore.web.resource.BookStoreResourceAssembler;
import lab.cloud.bookstore.web.service.BookStoreService;

import java.util.Map;

@RestController
@RequestMapping("/bookstores")
public class BookStoreController extends BaseController {

    private final BookStoreService bookStoreService;

    public BookStoreController(BookStoreService bookStoreService) {
        this.bookStoreService = bookStoreService;
    }

    @GetMapping("/{bookStoreId}")
    @PreAuthorize("hasAnyRole('ROLE_FULL_ACCESS','ROLE_READ_ONLY') and hasPermission(#bookStoreId, '')")
    public ResponseEntity<BookStoreResource> getBooks(@PathVariable String bookStoreId) {
        BookStore bookStore = bookStoreService.getBookStore(bookStoreId);
        return createResponse(bookStore);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> badBookStoreId(IllegalArgumentException e) {
        return super.badBookStoreId(e);
    }

    private ResponseEntity<BookStoreResource> createResponse(BookStore bookStore) {
        BookStoreResource bookStoreResource = new BookStoreResourceAssembler().toResource(bookStore);
        return new ResponseEntity<>(bookStoreResource, HttpStatus.OK);
    }
}