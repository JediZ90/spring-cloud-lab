package lab.cloud.bookstore.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lab.cloud.bookstore.web.model.Book;
import lab.cloud.bookstore.web.resource.BookResource;
import lab.cloud.bookstore.web.resource.BookResourceAssembler;
import lab.cloud.bookstore.web.service.BookStoreService;

@RestController
@RequestMapping("/bookstores/{bookStoreId}/books")
public class BookController extends BaseController {

    private final BookStoreService bookStoreService;

    public BookController(BookStoreService bookStoreService) {
        this.bookStoreService = bookStoreService;
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_FULL_ACCESS') and hasPermission(#bookStoreId, '')")
    public ResponseEntity<BookResource> addBook(@PathVariable String bookStoreId, @RequestBody Book book) {
        Book savedBook = bookStoreService.putBookInStore(bookStoreId, book);
        return createResponse(bookStoreId, savedBook, HttpStatus.CREATED);
    }

    @GetMapping("/{bookId}")
    @PreAuthorize("hasAnyRole('ROLE_FULL_ACCESS','ROLE_READ_ONLY') and hasPermission(#bookStoreId, '')")
    public ResponseEntity<BookResource> getBook(@PathVariable String bookStoreId, @PathVariable String bookId) {
        Book book = bookStoreService.getBookFromStore(bookStoreId, bookId);
        return createResponse(bookStoreId, book, HttpStatus.OK);
    }

    @DeleteMapping("/{bookId}")
    @PreAuthorize("hasRole('ROLE_FULL_ACCESS') and hasPermission(#bookStoreId, '')")
    public ResponseEntity<BookResource> deleteBook(@PathVariable String bookStoreId, @PathVariable String bookId) {
        Book book = bookStoreService.removeBookFromStore(bookStoreId, bookId);
        return createResponse(bookStoreId, book, HttpStatus.OK);
    }

    private ResponseEntity<BookResource> createResponse(String bookStoreId, Book book, HttpStatus httpStatus) {
        BookResource bookResource = new BookResourceAssembler().toResource(book, bookStoreId);
        return new ResponseEntity<>(bookResource, httpStatus);
    }
}