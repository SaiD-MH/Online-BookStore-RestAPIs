package com.springboot.bookstore.controller;

import com.springboot.bookstore.payload.BookDto;
import com.springboot.bookstore.payload.BookResponse;
import com.springboot.bookstore.service.BookService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import static com.springboot.bookstore.utils.AppConstants.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@Tag(name = "REST APIs for Book Resource")
public class BookController {


    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {

        return new ResponseEntity<>(bookService.createBook(bookDto), HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<BookResponse> getAllBooks(
            @RequestParam(value = "pageNo", defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = DEFAULT_SORT_DIRECTION, required = false) String sortDir


    ) {


        return ResponseEntity.ok(bookService.getAllBooks(pageNo , pageSize , sortBy ,sortDir));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable("id") long id) {

        return ResponseEntity.ok(bookService.getBookId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(@RequestBody BookDto bookDto, @PathVariable("id") long id) {

        return ResponseEntity.ok(bookService.updateBook(bookDto, id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable("id") long id) {

        return ResponseEntity.ok(bookService.deleteBook(id));
    }
}
